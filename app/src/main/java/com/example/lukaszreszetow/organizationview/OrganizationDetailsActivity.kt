package com.example.lukaszreszetow.organizationview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.organization_details_view.*
import kotlinx.android.synthetic.main.organization_news_item.view.*
import rx.Observable
import rx.Subscription
import retrofit2.Response
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription

class OrganizationDetailsActivity : AppCompatActivity() {

    var newsList = listOf<NewsObject>()
    val subscriptions = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.organization_details_view)
        setSupportActionBar(my_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        organizationDetailsCollapseBT.setOnClickListener {
            onBackPressed()
        }
        setupButtons()
        subscriptions.add(getOrganizationNews())
        if (intent.getBooleanExtra(IS_THIS_INVITATION_INTENT, true)) {
            organizationDetailsInvitationOverlay.visibility = View.VISIBLE
        }
    }

    private fun setupButtons() {
        organizationDetailsExpandNewsBT.setOnClickListener {
            val numberOfNewses = organizationDetailsNewsContainer.childCount
            organizationDetailsNewsContainer.removeAllViews()
            if (numberOfNewses <= 3) {
                organizationDetailsExpandNewsBT.text = "Collapse"
                newsList.forEach { news -> addOrganizationNewses(news) }
            } else {
                organizationDetailsExpandNewsBT.text = "Expand"
                newsList.take(3).forEach { news -> addOrganizationNewses(news) }
            }
        }
    }

    private fun getOrganizationNews(): Subscription? {
        return Observable.just(Response.success(newsArray))
            .observeOn(AndroidSchedulers.mainThread()).subscribe { lista ->
                val list = lista.body()
                newsList = list
                if (list.size > 3) {
                    organizationDetailsExpandNewsBT.visibility = View.VISIBLE
                }
                organizationDetailsNewsContainer.removeAllViews()
                list.take(3).forEach { news -> addOrganizationNewses(news) }
            }
    }

    private fun addOrganizationNewses(newsObject: NewsObject) {
        var organizationNews = layoutInflater.inflate(
            R.layout.organization_news_item,
            organizationDetailsNewsContainer,
            false
        )
        organizationNews.orgNewsTitle.text = newsObject.title
        organizationNews.orgNewsType.text = newsObject.type
        organizationNews.orgNewsDistance.text = newsObject.distance
        organizationNews.orgNewsDate.text = newsObject.date
        organizationDetailsNewsContainer.addView(organizationNews)
        organizationDetailsNewsContainer.invalidate()
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(0, R.anim.animation)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    data class NewsObject(
        val title: String,
        val type: String,
        val date: String,
        val distance: String
    )

    companion object {
        val newsArray = listOf(
            NewsObject(
                "Jakis ciekawy dlugi title dla wszystkihchsffaf",
                "Serious accident",
                "32m ago",
                "1,2km"
            ),
            NewsObject("Jakis ciekawy", "Not so serious accident", "Mon, 12:00", "20km"),
            NewsObject(
                "Jakis ciekawy dlugi title dla wszystkihchsffaf",
                "Serious accident",
                "32m ago",
                "1,2km"
            ),
            NewsObject(
                "Jakis ciekawy dlugi title dla wszystkihchsffaf",
                "Serious accident",
                "32m ago",
                "1,2km"
            )
        )

        const val IS_THIS_INVITATION_INTENT = "IS_THIS_INVITATION_INTENT"
    }
}
