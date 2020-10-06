package com.topnotch.developers.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.test.espresso.IdlingResource;

import com.topnotch.developers.R;
import com.topnotch.developers.adapter.GithubAdapter;
import com.topnotch.developers.databinding.ActivityMainBinding;
import com.topnotch.developers.dialogs.CustomProgressDialog;
import com.topnotch.developers.dialogs.DialogInternetCheck;
import com.topnotch.developers.dialogs.DialogNoRecord;
import com.topnotch.developers.interfaces.OnInternetRetryListener;
import com.topnotch.developers.model.GithubUsers;
import com.topnotch.developers.presenter.GithubPresenter;
import com.topnotch.developers.util.EspressoIdlingResource;
import com.topnotch.developers.util.NetworkUtility;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GithubUsersView, SwipeRefreshLayout.OnRefreshListener, OnInternetRetryListener {
    private GithubAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<String> usernames;
    private List<String> imageUrls;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NetworkUtility networkUtility = new NetworkUtility();
    private String query = "location:Nairobi";
    private ActivityMainBinding mainActivityBinding;
    private CustomProgressDialog progressDialog = new CustomProgressDialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityBinding = ActivityMainBinding.inflate(getLayoutInflater());
        swipeRefreshLayout = mainActivityBinding.swipeToReflesh;
        swipeRefreshLayout.setOnRefreshListener(this);
        loadGithubUsers();
    }

    @Override
    public void githubReadyUsers(List<GithubUsers> githubUsers) {
        usernames = new ArrayList<>();
        imageUrls = new ArrayList<>();
        if (githubUsers != null && githubUsers.size() > 0) {
            for (GithubUsers githubUser : githubUsers) {
                imageUrls.add(githubUser.getProfileImage());
                usernames.add(githubUser.getUserName());
            }
            progressDialog.dialog.dismiss();
            initRecyclerView();
            EspressoIdlingResource.decrement();
        } else {
            progressDialog.dialog.dismiss();
            showNoRecordDialog(query.replace("location:","").replace("+", " "));
        }
    }

    public void initRecyclerView() {
        recyclerView = mainActivityBinding.recyclerView;
        if (getResources().getDisplayMetrics().widthPixels > getResources().getDisplayMetrics().heightPixels) {
            layoutManager = new GridLayoutManager(this, 3);
        } else {
            layoutManager = new GridLayoutManager(this, 2);
        }
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

    public void loadGithubUsers() {
        if (networkUtility.networkAvailable(this)) {
            GithubPresenter presenter = new GithubPresenter(this);
            presenter.getGithubUsers(query);
            EspressoIdlingResource.increment();
            progressDialog.show(this, "Fetching Developers in your Country", "Please wait...");
        } else {
            showInternetDialog();
        }
    }

    public void searchDeveloper(String q) {
        if (networkUtility.networkAvailable(this)) {
            GithubPresenter presenter = new GithubPresenter(this);
            presenter.getGithubUsers(q);
            EspressoIdlingResource.increment();
        } else {
            showInternetDialog();
        }
    }

    private void showInternetDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DialogInternetCheck dialogInternetCheck = new DialogInternetCheck();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.add(android.R.id.content, dialogInternetCheck, "Internet Dialog").addToBackStack(null).commit();
    }

    private void showNoRecordDialog(String query) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DialogNoRecord dialogNoRecord = new DialogNoRecord(query);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.add(android.R.id.content, dialogNoRecord, "No record found Dialog").addToBackStack(null).commit();
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }

    //Activate search menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =
                getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                query = s.replaceAll(" ", "+");
                usernames = new ArrayList<>();
                imageUrls = new ArrayList<>();
                searchDeveloper(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                query = s.replaceAll(" ", "+");
                usernames = new ArrayList<>();
                imageUrls = new ArrayList<>();
                searchDeveloper(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onRetry(boolean retry) {
        loadGithubUsers();
    }
}
