package com.example.albert.webnotifier;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Albert on 2018/3/8.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    List<User> users;
    Context context;


    public UserAdapter(List<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UserAdapter.ViewHolder holder, int position) {

        holder.urlName.setText(users.get(position).getUrlName());
        holder.url.setText(users.get(position).getUrl());


        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String TAG = "onClick";
                Log.d(TAG, "onClick: pressing list: " + holder.urlName.getText());
                Toast.makeText(context, holder.urlName.getText(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, EditingURL.class);
                intent.putExtra("urlName", holder.urlName.getText());
                intent.putExtra("url", holder.url.getText());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView urlName;
        public TextView url;
        public View root;

        public ViewHolder(View itemView) {
            super(itemView);
            urlName = itemView.findViewById(R.id.text_url_name);
            url = itemView.findViewById(R.id.text_url);
            root = itemView;
        }
    }
}
