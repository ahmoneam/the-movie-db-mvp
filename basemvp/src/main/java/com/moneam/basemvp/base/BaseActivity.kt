package com.moneam.basemvp.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity

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

    override fun showError(@StringRes error: Int) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun showError(@NonNull error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }
}