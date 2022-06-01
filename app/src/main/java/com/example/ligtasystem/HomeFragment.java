package com.example.ligtasystem;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    //private static final String STATS_URL = "https://api.covid19api.com/summary";
    private static final String STATS_URL = "https://disease.sh/v3/covid-19/all";
    private static final String STATS_URL2 = "https://corona.lmao.ninja/v2/countries/philippines";

    SwipeRefreshLayout swipeRefreshLayout;



    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private ProgressBar progressBar;
    private TextView totalCasesTv, newCasesTv, totalDeathsTv, newDeathsTv, totalRecoveredTv, newRecoveredTv;
    private TextView phTotalCasesTv, phNewCasesTv, phTotalDeathsTv, phNewDeathsTv, phTotalRecoveredTv, phNewRecoveredTv;
    Button countriesBtn;



    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);


        countriesBtn = view.findViewById(R.id.countriesBtn);


        progressBar = view.findViewById(R.id.progressBar);
        totalCasesTv = view.findViewById(R.id.totalCasesTv);
        newCasesTv = view.findViewById(R.id.newCasesTv);
        totalDeathsTv = view.findViewById(R.id.totalDeathsTv);
        newDeathsTv = view.findViewById(R.id.newDeathsTv);
        totalRecoveredTv = view.findViewById(R.id.totalRecoveredTv);
        newRecoveredTv = view.findViewById(R.id.newRecoveredTv);

        progressBar = view.findViewById(R.id.progressBar);
        phTotalCasesTv = view.findViewById(R.id.phTotalCasesTv);
        phNewCasesTv = view.findViewById(R.id.phNewCasesTv);
        phTotalDeathsTv = view.findViewById(R.id.phTotalDeathsTv);
        phNewDeathsTv = view.findViewById(R.id.phNewDeathsTv);
        phTotalRecoveredTv = view.findViewById(R.id.phTotalRecoveredTv);
        phNewRecoveredTv = view.findViewById(R.id.phNewRecoveredTv);

        progressBar.setVisibility(View.GONE);

        loadHomeData2();


        countriesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),Countries.class);
                startActivity(intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadHomeData1();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;


    }

    @Override
    public void onResume() {
        loadHomeData2();
        loadHomeDataPh2();
        super.onResume();
    }

    private void loadHomeData1(){

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
                Toast.makeText(getContext(),""+error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

    private void loadHomeData2(){

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
                Toast.makeText(getContext(),""+error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private void loadHomeDataPh1(){

        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, STATS_URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                handleResponsePh(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),""+error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

    private void loadHomeDataPh2(){

        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, STATS_URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                handleResponsePh(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),""+error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    /*private void handleResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);

            JSONObject globalJO = jsonObject.getJSONObject("Global");

            String newConfirmed = globalJO.getString("NewConfirmed");
            String totalConfirmed = globalJO.getString("TotalConfirmed");
            String newDeaths = globalJO.getString("NewDeaths");
            String totalDeaths = globalJO.getString("TotalDeaths");
            String newRecovered = globalJO.getString("NewRecovered");
            String totalRecovered = globalJO.getString("TotalRecovered");

            totalCasesTv.setText(totalConfirmed);
            newCasesTv.setText(newConfirmed);
            totalDeathsTv.setText(totalDeaths);
            newDeathsTv.setText(newDeaths);
            totalRecoveredTv.setText(totalRecovered);
            newRecoveredTv.setText(newRecovered);

        }
        catch (Exception e){
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }


    }

     */

    private void handleResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);

            String newConfirmed = jsonObject.getString("todayCases");
            String totalConfirmed = jsonObject.getString("cases");
            String newDeaths = jsonObject.getString("todayDeaths");
            String totalDeaths = jsonObject.getString("deaths");
            String newRecovered = jsonObject.getString("todayRecovered");
            String totalRecovered = jsonObject.getString("recovered");

            String newConfirmedFormatted = new DecimalFormat("#,###.##").format(Double.parseDouble(newConfirmed));
            String totalConfirmedFormatted = new DecimalFormat("#,###.##").format(Double.parseDouble(totalConfirmed));
            String newDeathsFormatted = new DecimalFormat("#,###.##").format(Double.parseDouble(newDeaths));
            String totalDeathsFormatted = new DecimalFormat("#,###.##").format(Double.parseDouble(totalDeaths));
            String newRecoveredFormatted = new DecimalFormat("#,###.##").format(Double.parseDouble(newRecovered));
            String totalRecoveredFormatted = new DecimalFormat("#,###.##").format(Double.parseDouble(totalRecovered));

            newCasesTv.setText(newConfirmedFormatted);
            totalCasesTv.setText(totalConfirmedFormatted);
            newDeathsTv.setText(newDeathsFormatted);
            totalDeathsTv.setText(totalDeathsFormatted);
            newRecoveredTv.setText(newRecoveredFormatted);
            totalRecoveredTv.setText(totalRecoveredFormatted);

            progressBar.setVisibility(View.GONE);


        }
        catch (Exception e){
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }


    }

    private void handleResponsePh(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);

            String newConfirmed = jsonObject.getString("todayCases");
            String totalConfirmed = jsonObject.getString("cases");
            String newDeaths = jsonObject.getString("todayDeaths");
            String totalDeaths = jsonObject.getString("deaths");
            String newRecovered = jsonObject.getString("todayRecovered");
            String totalRecovered = jsonObject.getString("recovered");

            String newConfirmedFormatted = new DecimalFormat("#,###.##").format(Double.parseDouble(newConfirmed));
            String totalConfirmedFormatted = new DecimalFormat("#,###.##").format(Double.parseDouble(totalConfirmed));
            String newDeathsFormatted = new DecimalFormat("#,###.##").format(Double.parseDouble(newDeaths));
            String totalDeathsFormatted = new DecimalFormat("#,###.##").format(Double.parseDouble(totalDeaths));
            String newRecoveredFormatted = new DecimalFormat("#,###.##").format(Double.parseDouble(newRecovered));
            String totalRecoveredFormatted = new DecimalFormat("#,###.##").format(Double.parseDouble(totalRecovered));

            phNewCasesTv.setText(newConfirmedFormatted);
            phTotalCasesTv.setText(totalConfirmedFormatted);
            phNewDeathsTv.setText(newDeathsFormatted);
            phTotalDeathsTv.setText(totalDeathsFormatted);
            phNewRecoveredTv.setText(newRecoveredFormatted);
            phTotalRecoveredTv.setText(totalRecoveredFormatted);

            progressBar.setVisibility(View.GONE);


        }
        catch (Exception e){
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }


    }

}