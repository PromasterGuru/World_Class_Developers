package com.topnotch.developers.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.topnotch.developers.R;
import com.topnotch.developers.interfaces.IOnFilterListener;
import com.topnotch.developers.model.GithubUser;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GithubAdapter extends RecyclerView.Adapter<GithubAdapter.ViewHolder> implements Filterable {

    private final View.OnClickListener onClickListener;
    private IOnFilterListener onFilterListener;
    private List<GithubUser> githubUsers = new ArrayList<>();
    private List<GithubUser> filteredGithubUsers = new ArrayList<>();

    public GithubAdapter(View.OnClickListener onClickListener, IOnFilterListener onFilterListener) {
        this.onClickListener = onClickListener;
        this.onFilterListener = onFilterListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_users, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> onClickListener.onClick(((ViewGroup) v).getChildAt(0)));
        holder.bind(filteredGithubUsers.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return filteredGithubUsers.size();
    }

    public void clear() {
        this.githubUsers.clear();
        this.filteredGithubUsers.clear();
        notifyItemRangeChanged(0, getItemCount());
    }

    public void updateUsers(List<GithubUser> users) {
        this.githubUsers.addAll(users);
        this.filteredGithubUsers.addAll(users);
        notifyItemRangeInserted(getItemCount() - users.size(), users.size());
        notifyItemRangeChanged(getItemCount() - users.size(), users.size());
    }

    @Override
    public Filter getFilter() {
        final String[] keyWord = new String[1];
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                keyWord[0] = charSequence.toString();
                if (keyWord[0].isEmpty()) {
                    filteredGithubUsers = githubUsers;
                } else {
                    List<GithubUser> filteredList = new ArrayList<>();
                    for (GithubUser githubUser : githubUsers) {
                        if (githubUser.getLogin().toLowerCase().contains(keyWord[0].toLowerCase())) {
                            filteredList.add(githubUser);
                        }
                    }
                    filteredGithubUsers = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredGithubUsers;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredGithubUsers = (List<GithubUser>) results.values;
                onFilterListener.onFilterListener(keyWord[0], filteredGithubUsers.isEmpty());
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View parent;
        public TextView tvUsername;
        public CircleImageView circleImageView;

        public ViewHolder(View v) {
            super(v);
            parent = ((ViewGroup) v).getChildAt(0);
            tvUsername = v.findViewById(R.id.txtUsername);
            circleImageView = v.findViewById(R.id.imgUser);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(parent);
        }

        public void bind(GithubUser githubUser) {
            tvUsername.setText(githubUser.getLogin());
            Picasso.get().load(githubUser.getAvatarUrl()).into(circleImageView);
            parent.setTag(githubUser);
        }
    }
}
