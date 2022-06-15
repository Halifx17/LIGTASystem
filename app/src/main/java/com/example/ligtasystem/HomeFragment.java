package com.example.ligtasystem;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    //private static final String STATS_URL = "https://api.covid19api.com/summary";
    private static final String STATS_URL = "https://disease.sh/v3/covid-19/all";
    private static final String STATS_URL2 = "https://disease.sh/v3/covid-19/countries/Philippines?strict=true";
    private static final String PH_FLAG_URL = "https://disease.sh/assets/img/flags/ph.png";
    private static final String GLOBAL_FLAG_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Flag_of_the_United_Nations.svg/640px-Flag_of_the_United_Nations.svg.png";


    SwipeRefreshLayout swipeRefreshLayout;



    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;


    RecyclerView recyclerView;
    MyAdapterAnnounce myAdapterAnnounce;
    DatabaseReference dbReference;
    ArrayList<Announcement> list;

    private ProgressBar progressBar, progressBar2;
    private TextView
            totalCasesTv,
            newCasesTv,
            totalDeathsTv,
            newDeathsTv,
            totalRecoveredTv,
            newRecoveredTv,
            phTotalCasesTv,
            phNewCasesTv,
            phTotalDeathsTv,
            phNewDeathsTv,
            phTotalRecoveredTv,
            phNewRecoveredTv;
    Button countriesBtn;
    ImageView phFlag, globalFlag;



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


        recyclerView = view.findViewById(R.id.recyclerAnnounce);
        dbReference = FirebaseDatabase.getInstance().getReference("Announcements");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);


        list = new ArrayList<>();
        myAdapterAnnounce = new MyAdapterAnnounce(getContext(),list);
        recyclerView.setAdapter(myAdapterAnnounce);

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Announcement announcement = dataSnapshot.getValue(Announcement.class);
                    list.add(announcement);
                }

                myAdapterAnnounce.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);

        countriesBtn = view.findViewById(R.id.countriesBtn);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar2 = view.findViewById(R.id.progressBar2);

        phTotalCasesTv = view.findViewById(R.id.phTotalCasesTv);
        phNewCasesTv = view.findViewById(R.id.phNewCasesTv);
        phTotalDeathsTv = view.findViewById(R.id.phTotalDeathsTv);
        phNewDeathsTv = view.findViewById(R.id.phNewDeathsTv);
        phTotalRecoveredTv = view.findViewById(R.id.phTotalRecoveredTv);
        phNewRecoveredTv = view.findViewById(R.id.phNewRecoveredTv);

        totalCasesTv = view.findViewById(R.id.totalCasesTv);
        newCasesTv = view.findViewById(R.id.newCasesTv);
        totalDeathsTv = view.findViewById(R.id.totalDeathsTv);
        newDeathsTv = view.findViewById(R.id.newDeathsTv);
        totalRecoveredTv = view.findViewById(R.id.totalRecoveredTv);
        newRecoveredTv = view.findViewById(R.id.newRecoveredTv);

        progressBar.setVisibility(View.GONE);
        progressBar2.setVisibility(View.GONE);

        phFlag = view.findViewById(R.id.phFlag);
        globalFlag = view.findViewById(R.id.globalFlag);

        loadHomeDataPh1();
        loadHomeData1();


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
                loadHomeDataPh2();
                loadHomeData2();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;


    }

    @Override
    public void onResume() {
        loadHomeDataPh1();
        loadHomeData1();
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
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

    private void loadHomeDataPh1(){

        progressBar2.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, STATS_URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                handleResponsePh(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar2.setVisibility(View.GONE);
                Toast.makeText(getContext(),""+error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private void loadHomeDataPh2(){

        progressBar2.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, STATS_URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                handleResponsePh(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar2.setVisibility(View.GONE);
                Toast.makeText(getContext(),""+error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }


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

            Glide.with(getContext()).load(GLOBAL_FLAG_URL).into(globalFlag);

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

            Glide.with(getContext()).load(PH_FLAG_URL).into(phFlag);

            progressBar2.setVisibility(View.GONE);

        }
        catch (Exception e){
            progressBar2.setVisibility(View.GONE);
            Toast.makeText(getContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }


    }

}