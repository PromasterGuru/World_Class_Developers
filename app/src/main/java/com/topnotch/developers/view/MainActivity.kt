package com.topnotch.developers.view

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.Menu
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.IdlingResource
import com.topnotch.developers.R
import com.topnotch.developers.adapter.GithubAdapter
import com.topnotch.developers.databinding.ActivityMainBinding
import com.topnotch.developers.dialogs.DialogNoRecord
import com.topnotch.developers.interfaces.*
import com.topnotch.developers.model.GithubUser
import com.topnotch.developers.presenterImpl.GithubUsersPresenterImpl
import com.topnotch.developers.service.GetUsersNoticeIntractorImpl
import com.topnotch.developers.util.EspressoIdlingResource
import java.util.*


class MainActivity : BaseActivity(), OnInternetRetryListener, RecyclerItemClickListener,
    IMainContract.usersListingView, SwipeRefreshLayout.OnRefreshListener, IOnRecordNotFound {
    private lateinit var binding: ActivityMainBinding
    private var adapter: GithubAdapter? = null
    private var page = 1
    private val PAGE_LIMIT = 20
    private var isLoading = false
    private var isLastPage = false
    private lateinit var presenter: IMainContract.usersPresenter
    private var query = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initRecyclerView()
        initProgressBar()
        query = "location:$country"
        presenter = GithubUsersPresenterImpl(this, GetUsersNoticeIntractorImpl())
        presenter.requestUsersDataFromServer(query, page, PAGE_LIMIT)
        binding.swipeRefresh.setOnRefreshListener(this)
    }

    private fun initRecyclerView() {
        val gridLayoutManager = GridLayoutManager(this, 2)
        adapter = GithubAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.addOnScrollListener(object :
            EndlessRecyclerOnScrollListener(gridLayoutManager) {
            override fun onScrolledToEnd() {
                if (!isLoading && !isLastPage) {
                    page += 1
                    presenter.requestUsersDataFromServer(query, page, PAGE_LIMIT)
                }
            }

        })
    }

    val country: String
        get() = try {
            val tm = this.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
            val locale = Locale("", tm.networkCountryIso.uppercase())
            locale.displayCountry
        } catch (ex: Exception) {
            Locale.getDefault().displayCountry
        }

    @get:VisibleForTesting
    val countingIdlingResource: IdlingResource
        get() = EspressoIdlingResource.getIdlingResource()

    //Activate search menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                presenter.requestUsersDataFromServer(query, page, PAGE_LIMIT)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
//                adapter.getFilter().filter(query)
                return false
            }
        })
        return true
    }

    override fun onRetry(retry: Boolean) {
        binding.swipeRefresh.isRefreshing = false
        presenter.requestUsersDataFromServer(query, page, PAGE_LIMIT)
    }

    override fun onItemClicked(githubUser: GithubUser) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("username", githubUser.login)
        intent.putExtra("imageUrl", githubUser.avatarUrl)
        startActivity(intent)
    }

    override fun showProgress() {
        isLoading = true
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        isLoading = false
        progressBar.visibility = View.GONE
    }

    override fun setDataToRecyclerView(githubUsers: List<GithubUser>) {
        if (githubUsers.isNotEmpty()) {
            adapter!!.addUsers(githubUsers)
        } else {
            showDialogFragment(DialogNoRecord(this, query), getString(R.string.no_record_found))
        }
        isLastPage = githubUsers.size < PAGE_LIMIT
    }

    override fun onResponseFailure(error: Any) {
        errorHandler(error)
    }

    override fun onRefresh() {
        progressBar.visibility = View.VISIBLE
        presenter.requestUsersDataFromServer(query, page, PAGE_LIMIT)
    }

    override fun onRecordNotFound(boolean: Boolean?) {
        query = "location:$country"
        presenter.requestUsersDataFromServer(query, page, PAGE_LIMIT)
    }
}