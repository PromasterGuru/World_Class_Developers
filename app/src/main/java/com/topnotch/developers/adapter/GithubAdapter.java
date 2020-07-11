package com.topnotch.developers.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.topnotch.developers.R;
import com.topnotch.developers.view.DetailActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GithubAdapter extends RecyclerView.Adapter<GithubAdapter.ViewHolder>{

    private final List<String> usernames;
    private final List<String> imageUrls;
    private final Context context;

    public GithubAdapter(Context context, List<String> usernames, List<String> imageUrls) {
        this.usernames = usernames;
        this.imageUrls = imageUrls;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final View itemView;
        public final TextView username;
        public final CircleImageView imageUrl;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(this);
            this.itemView = itemView;
            username = itemView.findViewById(R.id.txtUsername);
            imageUrl = itemView.findViewById(R.id.imgUser);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("username", usernames.get(getAdapterPosition()));
                    intent.putExtra("imageUrl", imageUrls.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_users, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.username.setText(usernames.get(viewHolder.getAdapterPosition()));
        Picasso.get().load(imageUrls.get(viewHolder.getAdapterPosition())).into(viewHolder.imageUrl);
    }

    @Override
    public int getItemCount() {
        if(usernames != null){
            return usernames.size();
        }
        else{
            return 0;
        }
    }
}
