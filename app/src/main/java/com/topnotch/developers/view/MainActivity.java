package com.topnotch.developers.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.test.espresso.IdlingResource;

import com.topnotch.developers.R;
import com.topnotch.developers.adapter.GithubAdapter;
import com.topnotch.developers.databinding.ActivityMainBinding;
import com.topnotch.developers.model.GithubUsers;
import com.topnotch.developers.presenter.GithubPresenter;
import com.topnotch.developers.util.EspressoIdlingResource;
import com.topnotch.developers.util.NetworkUtility;
import com.topnotch.developers.util.ToastMessage;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements GithubUsersView, SwipeRefreshLayout.OnRefreshListener {

    GithubAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private List<String> usernames;
    private List<String> imageUrls;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SweetAlertDialog dialog;
    private ToastMessage toast;
    NetworkUtility networkUtility = new NetworkUtility();
    private String query = "location:Nairobi";
    ActivityMainBinding mainActivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        swipeRefreshLayout = mainActivityBinding.swipeToReflesh;
        swipeRefreshLayout.setOnRefreshListener(this);
        loadGithubUsers();
    }

    @Override
    public void githubReadyUsers(List<GithubUsers> githubUsers) {
        usernames = new ArrayList<>();
        imageUrls = new ArrayList<>();
        if (githubUsers.size() > 0) {
            for (GithubUsers githubUser : githubUsers) {
                imageUrls.add(githubUser.getProfileImage());
                usernames.add(githubUser.getUserName());
            }
            initRecyclerView();
            dialog.dismissWithAnimation();
            EspressoIdlingResource.decrement();
        } else {
            String msg = "We couldn't find " + query.replace("+", " ");
            dialog.dismissWithAnimation();
            dialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
            dialog.setTitleText("No Record Found!");
            dialog.setContentText(msg);
            dialog.setConfirmClickListener(sweetAlertDialog -> {
                dialog.dismissWithAnimation();
                query = "location:Nairobi";
                loadGithubUsers();
            });
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    public void initRecyclerView() {
        recyclerView = mainActivityBinding.recyclerView;
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new GithubAdapter(this, usernames, imageUrls);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        loadGithubUsers();
        dialog.dismissWithAnimation();
        swipeRefreshLayout.setRefreshing(false);
    }

    public void loadGithubUsers() {
        if (networkUtility.networkAvailable(this)) {
            GithubPresenter presenter = new GithubPresenter(this);
            presenter.getGithubUsers(query);
            EspressoIdlingResource.increment();
            dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            dialog.setTitleText("Fetching Developers");
            dialog.setContentText("Please wait...");
            dialog.setCancelable(false);
            dialog.show();
        } else {
            noInternetDialog();
        }
    }

    public void searchDeveloper(String q) {
        if (networkUtility.networkAvailable(this)) {
            GithubPresenter presenter = new GithubPresenter(this);
            presenter.getGithubUsers(q);
            EspressoIdlingResource.increment();
        } else {
            noInternetDialog();
        }
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

    private void noInternetDialog() {
        dialog = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        dialog.setCustomImage(R.drawable.ic_wifi);
        dialog.setTitleText("No Internet Connection");
        dialog.setContentText("Make sure that WI-FI or mobile data is turned on, then try again");
        dialog.setConfirmClickListener(sweetAlertDialog -> {
            dialog.dismissWithAnimation();
            loadGithubUsers();
        });
        dialog.setCancelable(false);
        dialog.show();
    }
}
