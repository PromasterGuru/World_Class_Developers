package com.topnotch.developers.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.view.MenuCompat
import androidx.test.espresso.IdlingResource
import com.bumptech.glide.Glide
import com.topnotch.developers.R
import com.topnotch.developers.databinding.ActivityDetailBinding
import com.topnotch.developers.interfaces.IMainContract
import com.topnotch.developers.model.GithubUserProfile
import com.topnotch.developers.presenterImpl.GithubUserProfilePresenterIml
import com.topnotch.developers.service.GetProfileNoticeIntractorImpl
import com.topnotch.developers.util.DateUtils
import com.topnotch.developers.util.EspressoIdlingResource
import java.text.ParseException

class DetailActivity : BaseActivity(), IMainContract.profileDetailsView,
    IMainContract.GetProfileNoticeIntractor.OnFetchProfileFinishedListener {
    private lateinit var binding: ActivityDetailBinding
    private var username = ""
    private var profileUrl = ""
    private var presenter: IMainContract.profilePresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initProgressBar()
        username = intent.getStringExtra("username").toString()
        profileUrl = intent.getStringExtra("imageUrl").toString()
        displayProfilePicAndUsername(username, profileUrl)
        presenter = GithubUserProfilePresenterIml(this, GetProfileNoticeIntractorImpl())
        presenter!!.requestProfileDataFromServer(username)
    }

    private fun displayProfilePicAndUsername(username: String, profileUrl: String) {
        binding.userName.text = username
        Glide.with(this)
            .load(profileUrl)
            .placeholder(R.drawable.spinner)
            .error(R.mipmap.ic_launcher_round)
            .into(binding.userProfile)
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_detail_actions, menu)
        MenuCompat.setGroupDividerEnabled(menu, true)
        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }
        return true
    }

    fun shareProfile() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        val text = "Check out this awesome developer @$username, $profileUrl."
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(
            Intent.createChooser(
                shareIntent,
                getString(R.string.share)
            )
        )
    }

    fun closeApplication() {
        finish()
        moveTaskToBack(true)
    }

    fun callDeveloper() {
        val tel = "tel:" + getString(R.string.phone_number)
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse(tel)
        startActivity(callIntent)
    }

    fun emailDeveloper() {
        val email = "mailto:" + getString(R.string.email_address)
        val emailIntent = Intent(
            Intent.ACTION_SENDTO,
            Uri.parse(email)
        )
        startActivity(Intent.createChooser(emailIntent, getString(R.string.email_chooser)))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareProfile -> shareProfile()
            R.id.call_us -> callDeveloper()
            R.id.email_us -> emailDeveloper()
            R.id.logout -> closeApplication()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    @get:VisibleForTesting
    val countingIdlingResource: IdlingResource
        get() = EspressoIdlingResource.getIdlingResource()

    override fun onSuccess(githubUserProfile: GithubUserProfile) {
        try {
            binding.userOrgs.text =
                githubUserProfile.company ?: getString(R.string.user_has_no_organization)
            binding.userCreateDate.text = String.format(
                getString(R.string.joined_on),
                DateUtils.simpleMonthDayYearDate(githubUserProfile.createdAt ?: "")
            )
            binding.userFollowers.text = "${githubUserProfile.followers}"
            binding.userFollowing.text = "${githubUserProfile.following}"
            binding.userRepositories.text = "${githubUserProfile.publicRepos}"
            binding.userGists.text = "${githubUserProfile.publicGists}"
            binding.userBioInformation.text =
                githubUserProfile.bio ?: getString(R.string.user_has_no_bio)
            binding.rlProfile.setOnClickListener {
                val webView = findViewById<WebView>(R.id.help_webview)
                setContentView(webView)
                webView.webViewClient = WebViewController()
                webView.loadUrl(githubUserProfile.htmlUrl!!)
            }
        } catch (e: ParseException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onFailure(error: Any) {
        errorHandler(error)
    }

    @SuppressLint("ResourceType")
    override fun setDataToView(githubUserProfile: GithubUserProfile) {
        try {
            binding.userName.text = githubUserProfile.login
            binding.userOrgs.text =
                githubUserProfile.company ?: getString(R.string.user_has_no_organization)
            binding.userCreateDate.text = String.format(
                getString(R.string.joined_on),
                DateUtils.simpleMonthDayYearDate(githubUserProfile.createdAt ?: "")
            )
            binding.userFollowers.text = "${githubUserProfile.followers}"
            binding.userFollowing.text = "${githubUserProfile.following}"
            binding.userRepositories.text = "${githubUserProfile.publicRepos}"
            binding.userGists.text = "${githubUserProfile.publicGists}"
            binding.userBioInformation.text = githubUserProfile.bio ?: getString(R.string.user_has_no_bio)
            Glide.with(this)
                .load(githubUserProfile.avatarUrl)
                .placeholder(R.drawable.spinner)
                .error(R.mipmap.ic_launcher_round)
                .into(binding.userProfile)
            binding.rlProfile.setOnClickListener {
                setContentView(R.layout.web_view_layout)
                val webView: WebView = findViewById(R.id.help_webview)
                webView.settings.javaScriptEnabled = true
                webView.settings.setSupportZoom(true)
                webView.webViewClient = WebViewController()
                webView.loadUrl(githubUserProfile.htmlUrl!!)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    override fun showProgress() {
        View.VISIBLE.also { progressBar.visibility = it }
    }

    override fun hideProgress() {
        View.GONE.also { progressBar.visibility = it }
    }

    override fun onResponseFailure(error: Any) {
        errorHandler(error)
    }
}