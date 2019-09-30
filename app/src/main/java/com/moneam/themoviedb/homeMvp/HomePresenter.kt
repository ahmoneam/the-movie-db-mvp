package com.moneam.themoviedb.homeMvp

import com.moneam.basemvp.base.BasePresenter
import com.moneam.themoviedb.homeMvp.HomeContract.HomeIRepository
import com.moneam.themoviedb.homeMvp.HomeContract.HomeIView
import io.reactivex.functions.Consumer

class HomePresenter(view: HomeIView?, repository: HomeIRepository) :
    BasePresenter<HomeIView, HomeIRepository>(view, repository) {

    private var page = 1

    override fun onViewReady() {
        loadData()
    }

    fun loadNextPage() {
        page++
        loadData()
    }

    private fun loadData() {
        subscribe(repository.getPopularPeople(page),
            Consumer {
                view!!.addData(it.results)
            }
        )
    }

    fun loadFirstPage() {
        page = 1
        loadData()
    }
}