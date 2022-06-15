package com.example.ligtasystem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MyAdapterAnnounce extends RecyclerView.Adapter<MyAdapterAnnounce.MyViewHolder> {

    Context context;
    ArrayList<Announcement> list;
    DatabaseReference referenceId;


    public MyAdapterAnnounce(Context context, ArrayList<Announcement> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.announcement_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Announcement announcement = list.get(position);
        holder.title.setText(announcement.getTitle());
        holder.date.setText(announcement.getDate());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,AnnouncementView.class);
                intent.putExtra("titleExtra",announcement.getTitle());
                intent.putExtra("dateExtra",announcement.getDate());
                intent.putExtra("aboutExtra",announcement.getAbout());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView date, title;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titleTv);
            date = itemView.findViewById(R.id.dateTv);
            cardView = itemView.findViewById(R.id.announcementCard);

        }
    }
}
