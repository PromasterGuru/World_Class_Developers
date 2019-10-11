package com.example.convergecodelab.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.IdlingResource;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.convergecodelab.R;
import com.example.convergecodelab.adapter.GithubAdapter;
import com.example.convergecodelab.model.GithubUsers;
import com.example.convergecodelab.presenter.GithubPresenter;
import com.example.convergecodelab.util.EspressoIdlingResource;
import com.example.convergecodelab.util.NetworkUtility;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GithubUsersView, SwipeRefreshLayout.OnRefreshListener {

    ProgressDialog progressDialog;
    GithubAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private List<String> usernames;
    private List<String> imageUrls;
    SwipeRefreshLayout swipeRefreshLayout;
    String query = "location:Nairobi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.swipeToReflesh);
        swipeRefreshLayout.setOnRefreshListener(this);
        fetchDataHelper();
    }

    @Override
    public void githubReadyUsers(List<GithubUsers> githubUsers) {
        usernames = new ArrayList<>();
        imageUrls = new ArrayList<>();
        for (GithubUsers githubUser: githubUsers) {
            imageUrls.add(githubUser.getProfileImage());
            usernames.add(githubUser.getUserName());
        }
        initRecyclerView();
        progressDialog.dismiss();
        EspressoIdlingResource.decrement();
    }
    public void initRecyclerView(){
        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new GithubAdapter(this, usernames, imageUrls);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        loadGithubUsers();
        swipeRefreshLayout.setRefreshing(false);
    }

    public void loadGithubUsers(){
        GithubPresenter presenter = new GithubPresenter(this);
        presenter.getGithubUsers(query);
        EspressoIdlingResource.increment();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading Github Users...");
        progressDialog.setMessage("Please wait.");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void fetchDataHelper(){
        NetworkUtility networkUtility = new NetworkUtility();
        if(networkUtility.networkAvailable(this)) {
            loadGithubUsers();
        }
        else {
            Snackbar.make(findViewById(R.id.main_layout), "No internet connection", Snackbar.LENGTH_INDEFINITE).setDuration(60000)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            loadGithubUsers();
                        }
                    })
                    .show();
        }
    }

    @Override
    protected void onDestroy() {
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        super.onDestroy();
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }

    //Activate search menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =  (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                query = s.replaceAll(" ", "+");
                usernames = new ArrayList<>();
                imageUrls = new ArrayList<>();
                loadGithubUsers();
                progressDialog.cancel();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }
}