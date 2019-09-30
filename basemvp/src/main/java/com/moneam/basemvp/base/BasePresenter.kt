package com.moneam.basemvp.base

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

abstract class BasePresenter<View : BaseContract.BaseIView,
        Repository : BaseContract.BaseIRepository>(
    var view: View?,
    val repository: Repository
) : BaseContract.BaseIPresenter {

    val compositeDisposable = CompositeDisposable()

    fun <T> subscribe(
        observable: Observable<T>,
        success: Consumer<T>,
        error: Consumer<Throwable> = Consumer { },
        showLoading: Boolean = true
    ) {
        compositeDisposable.add(
            observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    if (showLoading)
                        view?.showLoading()
                }
                .doAfterTerminate {
                    if (showLoading)
                        view?.hideLoading()
                }
                .subscribe(success,
                    Consumer {
                        handelError(it)
                        error.accept(it)
                    })
        )
    }

    fun <T> subscribe(
        observable: Single<T>,
        success: Consumer<T>,
        error: Consumer<Throwable> = Consumer { },
        showLoading: Boolean = true
    ) {
        compositeDisposable.add(
            observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    if (showLoading)
                        view?.showLoading()
                }
                .doAfterTerminate {
                    if (showLoading)
                        view?.hideLoading()
                }
                .subscribe(success,
                    Consumer {
                        handelError(it)
                        error.accept(it)
                    })
        )
    }

    private fun handelError(it: Throwable) {
        when (it) {
            is IOException -> view?.showError(Status.Error(errorCode = ErrorStatus.NO_INTERNET_CONNECTION))
            is HttpException -> when (it.code()) {
                401 -> {
                    view?.showError(Status.Error(errorCode = ErrorStatus.UNAUTHORIZED))
                }
                404 -> {
                    view?.showError(Status.Error(errorCode = ErrorStatus.DATA_NOT_FOUND))
                }
                else -> {
                    view!!.showError(Status.Error(errorMessage = it.message!!))
                }

            }
            else -> view!!.showError(Status.Error(errorMessage = it.message!!))
        }
    }

    abstract override fun onViewReady()

    override fun onViewDestroy() {
        view = null
        compositeDisposable.clear()
    }
}

