package com.moneam.basemvp.base

import android.os.Bundle
import androidx.annotation.LayoutRes

interface BaseContract {
    interface BaseIView {
        fun onViewReady(bundle: Bundle?)
        fun showLoading()
        fun hideLoading()
        fun showError(error: Status.Error)
        @LayoutRes
        fun getLayoutResourceId(): Int

        fun showNoInternetConnection()
    }

    interface BaseIPresenter {
        fun onViewReady()
        fun onViewDestroy()
    }

    interface BaseIRepository {

    }
}