package com.example.convergecodelab.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.IdlingResource;
import android.support.v4.view.MenuCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.convergecodelab.R;
import com.example.convergecodelab.model.GithubUserProfile;
import com.example.convergecodelab.presenter.GithubProfilePresenter;
import com.example.convergecodelab.util.EspressoIdlingResource;
import com.example.convergecodelab.util.NetworkUtility;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity implements GithubUserProfileView {
    CircleImageView imgProfile;
    private WebView mWebview ;
    TextView txtUsername,txtCreate_date,txtOrg,txtFollowers,txtFollowing,txtRepos,txtGists,txtBio;
    LinearLayout layout;

    private String profileUrl;
    private String organization;
    private String joinDate;
    private String repos;
    private String followers;
    private String following;
    private String bioInfo;
    private String gists;
    private String username;
    ProgressDialog progressDialog;
    GithubProfilePresenter profilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imgProfile = (CircleImageView) findViewById(R.id.userProfile);
        txtUsername = (TextView)findViewById(R.id.userName);
        txtCreate_date = (TextView)findViewById(R.id.userCreate_date);
        txtOrg = (TextView)findViewById(R.id.userOrgs);
        txtFollowers = (TextView)findViewById(R.id.userFollowers);
        txtFollowing = (TextView)findViewById(R.id.userFollowing);
        txtRepos = (TextView)findViewById(R.id.userRepositories);
        txtGists = (TextView)findViewById(R.id.userGists);
        txtBio = (TextView)findViewById(R.id.userBioInformation);
         layout =
        (LinearLayout)findViewById(R.id.user_details);
        fetchDataHelper();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getReadyProfiles(GithubUserProfile githubUser) {
        this.profileUrl = githubUser.getProfileUrl();
        this.organization = githubUser.getCompany();
        this.joinDate = githubUser.getJoinDate();
        this.repos = githubUser.getRepos();
        this.followers = githubUser.getFollowers();
        this.following = githubUser.getFollowing();
        this.bioInfo = githubUser.getBio();
        this.gists = githubUser.getGists();
        getProfiles();
        progressDialog.dismiss();
        EspressoIdlingResource.decrement();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getProfiles(){
        try {
            joinDate = dateConverter();
        } catch (ParseException e) {
            Log.d("Error", "An error occurred " + e.getMessage());
        }
        if(this.organization == null){
            this.organization = "User has no organization";
        }
        if(this.bioInfo == null){
            this.bioInfo = "User has no bio";
        }
        String date = "Joined on " + joinDate;
        txtCreate_date.setText(date);
        txtOrg.setText(organization);
        txtFollowers.setText(followers);
        txtFollowing.setText(following);
        txtRepos.setText(repos);
        txtGists.setText(gists);
        txtBio.setText(bioInfo);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            setContentView(R.layout.web_view_layout);
            mWebview = (WebView)findViewById(R.id.help_webview);
            mWebview.getSettings().setJavaScriptEnabled(true);
            mWebview.setWebViewClient(new WebViewController());
            NetworkUtility networkUtility = new NetworkUtility();
            if(networkUtility.networkAvailable(getApplicationContext())) {
                mWebview.loadUrl(profileUrl);
            }
            else {
                Snackbar.make(findViewById(R.id.user_details), "No internet connection", Snackbar.LENGTH_INDEFINITE).setDuration(60000)
                    .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                        profilePresenter.getGithubProfiles(username);
                        }
                                                }).show();
            }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String dateConverter() throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM, d yyyy", Locale.ENGLISH);
        Date date = inputFormat.parse(joinDate);
        return outputFormat.format(date);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_detail_actions, menu);
        MenuCompat.setGroupDividerEnabled(menu,true);
        if(menu instanceof MenuBuilder){
            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true);
        }
        return true;
    }

    public void shareProfile(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        String text = "Check out this awesome developer @" + username + ", " +profileUrl +".";
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(shareIntent,
                getString(R.string.share)));
    }

    public void closeApplication(){
        finish();
        moveTaskToBack(true);
    }

    public void callDeveloper(){
        String tel = "tel:" + getString(R.string.phone_number);
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse(tel));
        startActivity(callIntent);
    }

    public void emailDeveloper(){
        String email = "mailto:" + getString(R.string.email_address);
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                Uri.parse(email));
        startActivity(Intent.createChooser(emailIntent,
                getString(R.string.email_chooser)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.shareProfile:
                shareProfile();
                break;

            case R.id.call_us:
                callDeveloper();
                break;

            case R.id.email_us:
                emailDeveloper();
                break;

            case R.id.logout:
                closeApplication();
                break;

            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }
    public void fetchDataHelper(){
        EspressoIdlingResource.increment();
        NetworkUtility networkUtility = new NetworkUtility();
        if(networkUtility.networkAvailable(this)) {
            Intent intent = getIntent();
            progressDialog = new ProgressDialog(this);
            username = intent.getStringExtra("username");
            String msg = "Loading " + username + " Info...";
            progressDialog.setTitle(msg);
            progressDialog.setMessage("Please wait.");
            progressDialog.setCancelable(false);
            progressDialog.show();
            profilePresenter= new GithubProfilePresenter(this);
            profilePresenter.getGithubProfiles(username);
            Picasso.with(this).load(intent.getStringExtra("imageUrl")).into(imgProfile);
            txtUsername.setText(username);
        }
        else {
            Snackbar.make(findViewById(R.id.user_details), "No internet connection", Snackbar.LENGTH_INDEFINITE).setDuration(60000)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            profilePresenter.getGithubProfiles(username);
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
}
