package com.moneam.basemvp.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.moneam.basemvp.BuildConfig

abstract class BaseActivity<Presenter : BasePresenter<*, *>>
    : AppCompatActivity(),
    BaseContract.BaseIView {

    abstract val presenter: Presenter

    @LayoutRes
    abstract override fun getLayoutResourceId(): Int

    abstract override fun onViewReady(bundle: Bundle?)

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())
        // implement some code

        onViewReady(savedInstanceState)

        presenter.onViewReady()
    }

    override fun onDestroy() {
        presenter.onViewDestroy()
        super.onDestroy()
    }

    override fun showLoading() {
        Toast.makeText(this, "Loading", Toast.LENGTH_LONG).show()
    }

    override fun hideLoading() {
        Toast.makeText(this, "Loaded", Toast.LENGTH_LONG).show()
    }

    override fun showError(error: Status.Error) {
        when {
            error.errorCode != null -> {
                when (error.errorCode) {
                    ErrorStatus.NO_INTERNET_CONNECTION -> {
                        showNoInternetConnection()
                    }
                    ErrorStatus.UNAUTHORIZED -> {
                        if (BuildConfig.DEBUG) {
                            TODO("implement logout logic")
                            // todo
                            // fixme
                        }
                    }
                    ErrorStatus.DATA_NOT_FOUND -> {
                        if (BuildConfig.DEBUG) {
                            TODO("implement no data found logic")
                        }
                    }
                    else -> {
                        TODO()
                    }
                }
            }
            error.errorStringResource != null -> Toast.makeText(
                this,
                error.errorStringResource,
                Toast.LENGTH_LONG
            ).show()
            error.errorMessage != null -> Toast.makeText(
                this,
                error.errorMessage,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun showNoInternetConnection() {
        Toast.makeText(
            this,
            "no internet connection",
            Toast.LENGTH_LONG
        ).show()
    }
}