package com.example.ligtasystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterStat extends RecyclerView.Adapter<AdapterStat.HolderStat> {

    private Context context;
    public ArrayList<ModelStat> statArrayList;

    public AdapterStat(Context context, ArrayList<ModelStat> statArrayList) {
        this.context = context;
        this.statArrayList = statArrayList;
    }

    @NonNull
    @Override
    public HolderStat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_stat,parent,false);
        return new HolderStat(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderStat holder, int position) {

        ModelStat modelStat = statArrayList.get(position);

        String country = modelStat.getCountry();
        String cases = modelStat.getCases();
        String todayCases = modelStat.getTodayCases();
        String deaths = modelStat.getDeaths();
        String todayDeaths = modelStat.getTodayDeaths();
        String recovered = modelStat.getRecovered();
        String todayRecovered = modelStat.getTodayRecovered();

        holder.countryTv.setText(country);
        holder.casesTv.setText(cases);
        holder.todayCasesTv.setText(todayCases);
        holder.deathsTv.setText(deaths);
        holder.todayDeathsTv.setText(todayDeaths);
        holder.recoveredTv.setText(recovered);
        holder.todayRecoveredTv.setText(todayRecovered);

    }

    @Override
    public int getItemCount() {
        return statArrayList.size();
    }


    class HolderStat extends RecyclerView.ViewHolder{

        TextView countryTv, casesTv, todayCasesTv, deathsTv, todayDeathsTv, recoveredTv, todayRecoveredTv;


        public HolderStat(@NonNull View itemView) {
            super(itemView);

            countryTv = itemView.findViewById(R.id.countryTv);
            casesTv = itemView.findViewById(R.id.casesTv);
            todayCasesTv = itemView.findViewById(R.id.todayCasesTv);
            deathsTv = itemView.findViewById(R.id.deathsTv);
            todayDeathsTv = itemView.findViewById(R.id.todayDeathsTv);
            recoveredTv = itemView.findViewById(R.id.todayDeathsTv);
            todayRecoveredTv = itemView.findViewById(R.id.todayRecoveredTv);
        }
    }
}
