package com.moneam.themoviedb.homeMvp

import com.moneam.basemvp.base.BasePresenter
import com.moneam.themoviedb.homeMvp.HomeContract.HomeIRepository
import com.moneam.themoviedb.homeMvp.HomeContract.HomeIView

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
        view!!.showLoading()
        repository.getPopularPeople(page,
            success = {
                view!!.hideLoading()
                view!!.addData(it.results)
            },
            failure = {
                view!!.hideLoading()
                view!!.showError(it.message!!)
            })
    }
}