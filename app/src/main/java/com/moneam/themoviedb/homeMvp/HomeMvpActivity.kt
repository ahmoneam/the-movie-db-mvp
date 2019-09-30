package com.moneam.themoviedb.homeMvp

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.moneam.basemvp.base.BaseActivity
import com.moneam.basemvp.model.Actor
import com.moneam.themoviedb.ActorsAdapter
import com.moneam.themoviedb.EndlessRecyclerViewScrollListener
import com.moneam.themoviedb.R
import kotlinx.android.synthetic.main.activity_main.*

class HomeMvpActivity : BaseActivity<HomePresenter>(),
    HomeContract.HomeIView {
    private lateinit var adapter: ActorsAdapter

    override val presenter = HomePresenter(this, HomeRepository())

    override fun getLayoutResourceId() = R.layout.activity_main

    override fun onViewReady(bundle: Bundle?) {
        val linearLayoutManager = LinearLayoutManager(this)

        adapter = ActorsAdapter({ url: String, id: Int ->
            //            controller.loadImage(url, id)
        }, {
            // todo open details activity
            // fixme test
            // hgjgjgjhgj
        })
        rv_actors.layoutManager = linearLayoutManager
        rv_actors.adapter = adapter

        rv_actors.addOnScrollListener(object :
            EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                presenter.loadNextPage()
            }
        })

        tv_no_internet.setOnClickListener {
            presenter.loadFirstPage()
        }
    }

    override fun addData(results: List<Actor>) {
        adapter.add(results)
        rv_actors.visibility = View.VISIBLE
        tv_no_internet.visibility = View.GONE
    }

    override fun showNoInternetConnection() {
        rv_actors.visibility = View.GONE
        tv_no_internet.visibility = View.VISIBLE
    }
}
