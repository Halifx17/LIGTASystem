package com.example.ligtasystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Countries extends AppCompatActivity {

    private static final String STATS_URL = "https://disease.sh/v3/covid-19/countries";

    ProgressBar progressBar;
    EditText searchEt;
    ImageButton sortBtn;
    RecyclerView statsRv;
    SwipeRefreshLayout swipeRefreshLayout;


    ArrayList<ModelStat>statArrayList;
    AdapterStat adapterStat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        progressBar = findViewById(R.id.progressBarCountry);
        searchEt = findViewById(R.id.searchEt);
        sortBtn = findViewById(R.id.sortBtn);
        statsRv = findViewById(R.id.statsRv);

        loadStatsData();

        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                try {
                    adapterStat.getFilter().filter(s);

                }
                catch (Exception e){
                    e.printStackTrace();

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        PopupMenu popupMenu = new PopupMenu(getApplicationContext(), sortBtn);
        popupMenu.getMenu().add(Menu.NONE,0,0,"Ascending");
        popupMenu.getMenu().add(Menu.NONE,1,1,"Descending");
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == 0){
                    Collections.sort(statArrayList, new SortStatCountryAsc());
                    adapterStat.notifyDataSetChanged();
                }
                else if (id == 1){
                    Collections.sort(statArrayList, new SortStatCountryDesc());
                    adapterStat.notifyDataSetChanged();
                }

                return false;
            }
        });


        sortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popupMenu.show();

            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadStatsData1();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onResume() {
        loadStatsData();
        super.onResume();
    }

    private  void loadStatsData(){
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, STATS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                handleResponse(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),""+error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private  void loadStatsData1(){
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, STATS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                handleResponse(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),""+error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

    private void handleResponse(String response) {

        statArrayList = new ArrayList<>();
        statArrayList.clear();

        try {



            JSONArray jsonArray = new JSONArray(response);


            for(int i=0; i<jsonArray.length(); i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String countryName = jsonObject.getString("country");
                String cases = jsonObject.getString("cases");
                String todayCases = jsonObject.getString("todayCases");
                String deaths = jsonObject.getString("deaths");
                String todayDeaths = jsonObject.getString("todayDeaths");
                String recovered = jsonObject.getString("recovered");
                String todayRecovered = jsonObject.getString("todayRecovered");


                String casesFormatted = new DecimalFormat("#,###.##").format(Double.parseDouble(cases));
                String todayCasesFormatted = new DecimalFormat("#,###.##").format(Double.parseDouble(todayCases));
                String deathsFormatted = new DecimalFormat("#,###.##").format(Double.parseDouble(deaths));
                String todayDeathsFormatted = new DecimalFormat("#,###.##").format(Double.parseDouble(todayDeaths));
                String recoveredFormatted = new DecimalFormat("#,###.##").format(Double.parseDouble(recovered));
                String todayRecoveredFormatted = new DecimalFormat("#,###.##").format(Double.parseDouble(todayRecovered));

                JSONObject object = jsonObject.getJSONObject("countryInfo");
                String flagUrl = object.getString("flag");

                ModelStat modelStat = new ModelStat(countryName,
                        casesFormatted,
                        todayCasesFormatted,
                        deathsFormatted,
                        todayDeathsFormatted,
                        recoveredFormatted,
                        todayRecoveredFormatted,
                        flagUrl);
                statArrayList.add(modelStat);
            }

            adapterStat = new AdapterStat(getApplicationContext(),statArrayList);
            statsRv.setAdapter(adapterStat);

            progressBar.setVisibility(View.GONE);

        }
        catch (Exception e){

            progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }


    }

    public  class SortStatCountryAsc implements Comparator<ModelStat>{

        @Override
        public int compare(ModelStat left, ModelStat right) {
            return left.getCountry().compareTo(right.getCountry());
        }
    }

    public class SortStatCountryDesc implements Comparator<ModelStat>{

        @Override
        public int compare(ModelStat left, ModelStat right) {
            return right.getCountry().compareTo(left.getCountry());
        }
    }



}