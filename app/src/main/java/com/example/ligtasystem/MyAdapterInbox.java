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

import java.util.ArrayList;

public class MyAdapterInbox extends RecyclerView.Adapter<MyAdapterInbox.MyViewHolder> {

    Context context;
    ArrayList<InboxClass> list;

    public MyAdapterInbox(Context context, ArrayList<InboxClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.inbox_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        InboxClass inboxClass = list.get(position);
        holder.type.setText(inboxClass.getType());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,InboxView.class);
                intent.putExtra("type",inboxClass.getType());
                intent.putExtra("concern",inboxClass.getConcern());
                intent.putExtra("username",inboxClass.getUsername());
                intent.putExtra("firstname",inboxClass.getFirstname());
                intent.putExtra("profileUri",inboxClass.getProfileUri());
                intent.putExtra("lastname",inboxClass.getFirstname());
                intent.putExtra("email",inboxClass.getEmail());
                intent.putExtra("address",inboxClass.getAddress());
                intent.putExtra("phoneNumber",inboxClass.getPhoneNumber());
                intent.putExtra("password",inboxClass.getPassword());
                intent.putExtra("birthDate",inboxClass.getBirthdate());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView type, date;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            type = itemView.findViewById(R.id.inboxTypeTv);
            cardView = itemView.findViewById(R.id.inboxCard);
        }
    }
}
