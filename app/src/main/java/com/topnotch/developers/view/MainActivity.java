package com.topnotch.developers.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.test.espresso.IdlingResource;

import com.topnotch.developers.R;
import com.topnotch.developers.adapter.GithubAdapter;
import com.topnotch.developers.databinding.ActivityMainBinding;
import com.topnotch.developers.dialogs.DialogInternetCheck;
import com.topnotch.developers.interfaces.EndlessRecyclerOnScrollListener;
import com.topnotch.developers.interfaces.IGithubUsersView;
import com.topnotch.developers.interfaces.IOnFilterListener;
import com.topnotch.developers.interfaces.OnInternetRetryListener;
import com.topnotch.developers.model.GithubUser;
import com.topnotch.developers.presenter.GithubPresenter;
import com.topnotch.developers.util.EspressoIdlingResource;
import com.topnotch.developers.util.NetworkUtility;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements IGithubUsersView, SwipeRefreshLayout.OnRefreshListener, OnInternetRetryListener, View.OnClickListener, IOnFilterListener {
    private GithubAdapter adapter;
    private GithubPresenter presenter;
    private NetworkUtility networkUtility = new NetworkUtility();
    private String query;
    private ActivityMainBinding binding;
    private int page = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        query = "location:" + getCountry();
        loadGithubUsers(query, page);

        binding.swipeToReflesh.setOnRefreshListener(this);
        adapter = new GithubAdapter(this, this);
        gridLayoutManager = new GridLayoutManager(this, 2);
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onScrolledToEnd() {
                if (!isLoading && !isLastPage) {
                    page++;
                    isLoading = true;
                    loadGithubUsers(query, page);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void githubReadyUsers(List<GithubUser> users) {
        isLoading = false;
        binding.swipeToReflesh.setRefreshing(false);
        binding.recyclerView.setVisibility(View.VISIBLE);
        adapter.updateUsers(users);
        EspressoIdlingResource.decrement();
    }

    @Override
    public void displayUser(GithubUser githubUser) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("username", githubUser.getLogin());
        intent.putExtra("imageUrl", githubUser.getAvatarUrl());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        loadGithubUsers(query, page);
    }

    public void loadGithubUsers(String query, int page) {
        if (networkUtility.networkAvailable(this)) {
            binding.swipeToReflesh.setRefreshing(true);
            int per_page = 100;
            presenter = new GithubPresenter(this, query, page, per_page);
            EspressoIdlingResource.increment();
        } else {
            showInternetDialog();
        }
    }

    public void searchDeveloper(String q) {
        isLoading = true;
        if (networkUtility.networkAvailable(this)) {
            presenter = new GithubPresenter(this, q, 0, 1);
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

    public String getCountry() {
        try {
            TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            Locale locale = new Locale("", tm.getNetworkCountryIso().toUpperCase());
            return locale.getDisplayCountry();
        } catch (Exception ex) {
            return Locale.getDefault().getDisplayCountry();
        }
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }

    //Activate search menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchDeveloper(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onRetry(boolean retry) {
        loadGithubUsers(query, page);
    }

    @Override
    public void onClick(View v) {
        this.presenter.displayClickedUser((GithubUser) v.getTag());
    }

    @Override
    public void onFilterListener(String keyWord, Boolean isFound) {
        if (isFound) {
            binding.recyclerView.setVisibility(View.GONE);
        }
    }
}
