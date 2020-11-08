package com.topnotch.developers.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.view.MenuCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.espresso.IdlingResource;

import com.squareup.picasso.Picasso;
import com.topnotch.developers.R;
import com.topnotch.developers.dialogs.DialogInternetCheck;
import com.topnotch.developers.interfaces.IGithubUserProfileView;
import com.topnotch.developers.model.GithubUserProfile;
import com.topnotch.developers.presenter.GithubProfilePresenter;
import com.topnotch.developers.util.EspressoIdlingResource;
import com.topnotch.developers.util.NetworkUtility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity implements IGithubUserProfileView {
    CircleImageView imgProfile;
    private WebView mWebview;
    TextView txtUsername, txtCreate_date, txtOrg, txtFollowers, txtFollowing, txtRepos, txtGists, txtBio;
    LinearLayout layout;
    private String profileUrl;
    private String organization;
    private String joinDate;
    private Integer repos;
    private Integer followers;
    private Integer following;
    private String bioInfo;
    private Integer gists;
    private String username;
    private GithubProfilePresenter profilePresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imgProfile = findViewById(R.id.userProfile);
        txtUsername = findViewById(R.id.userName);
        txtCreate_date = findViewById(R.id.userCreate_date);
        txtOrg = findViewById(R.id.userOrgs);
        txtFollowers = findViewById(R.id.userFollowers);
        txtFollowing = findViewById(R.id.userFollowing);
        txtRepos = findViewById(R.id.userRepositories);
        txtGists = findViewById(R.id.userGists);
        txtBio = findViewById(R.id.userBioInformation);
        layout = findViewById(R.id.user_details);
        fetchDataHelper();
    }

    @Override
    public void getReadyProfiles(GithubUserProfile githubUser) {
        if (githubUser != null) {
            this.profileUrl = githubUser.getAvatarUrl();
            this.organization = githubUser.getCompany();
            this.joinDate = githubUser.getCreatedAt();
            this.repos = githubUser.getPublicRepos();
            this.followers = githubUser.getFollowers();
            this.following = githubUser.getFollowing();
            this.bioInfo = githubUser.getBio();
            this.gists = githubUser.getPublicGists();
            setProfile();
            EspressoIdlingResource.decrement();
        } else {
            Toast.makeText(this, "An error occured while fetching user information", Toast.LENGTH_LONG).show();
        }
    }

    public void setProfile() {
        try {
            joinDate = dateConverter();
            if (this.organization == null) {
                this.organization = "No organization found";
            }
            if (this.bioInfo == null) {
                this.bioInfo = "No bio information found";
            }
            String date = "Joined on " + joinDate;
            txtCreate_date.setText(date);
            txtOrg.setText(organization);
            txtFollowers.setText(String.valueOf(followers));
            txtFollowing.setText(String.valueOf(following));
            txtRepos.setText(String.valueOf(repos));
            txtGists.setText(String.valueOf(gists));
            txtBio.setText(bioInfo);
            layout.setOnClickListener((View.OnClickListener) v -> {
                NetworkUtility networkUtility = new NetworkUtility();
                if (networkUtility.networkAvailable(getApplicationContext())) {
                    setContentView(R.layout.web_view_layout);
                    mWebview = (WebView) findViewById(R.id.help_webview);
                    mWebview.setWebViewClient(new WebViewController());
                    mWebview.loadUrl(profileUrl);
                } else {
                    showInternetDialog();
                }
            });
        } catch (ParseException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("Error", "An error occurred " + e.getMessage());
        }
    }

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
        MenuCompat.setGroupDividerEnabled(menu, true);
        if (menu instanceof MenuBuilder) {
            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true);
        }
        return true;
    }

    public void shareProfile() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        String text = "Check out this awesome developer @" + username + ", " + profileUrl + ".";
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(shareIntent,
                getString(R.string.share)));
    }

    public void closeApplication() {
        finish();
        moveTaskToBack(true);
    }

    public void callDeveloper() {
        String tel = "tel:" + getString(R.string.phone_number);
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse(tel));
        startActivity(callIntent);
    }

    public void emailDeveloper() {
        String email = "mailto:" + getString(R.string.email_address);
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                Uri.parse(email));
        startActivity(Intent.createChooser(emailIntent,
                getString(R.string.email_chooser)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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

    public void fetchDataHelper() {
        EspressoIdlingResource.increment();
        NetworkUtility networkUtility = new NetworkUtility();
        if (networkUtility.networkAvailable(this)) {
            Intent intent = getIntent();
            username = intent.getStringExtra("username");
            profilePresenter = new GithubProfilePresenter(this, username);
            Picasso.get().load(intent.getStringExtra("imageUrl")).into(imgProfile);
            txtUsername.setText(username);
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

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
