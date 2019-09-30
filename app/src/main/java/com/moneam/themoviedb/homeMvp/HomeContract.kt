package com.moneam.themoviedb.homeMvp

import com.moneam.basemvp.base.BaseContract
import com.moneam.basemvp.model.Actor
import com.moneam.basemvp.model.ActorsResponse
import io.reactivex.Single

interface HomeContract {
    interface HomeIView : BaseContract.BaseIView {
        fun addData(results: List<Actor>)
    }

    interface HomeIPresenter : BaseContract.BaseIPresenter {

    }

    interface HomeIRepository : BaseContract.BaseIRepository {

        fun getPopularPeople(
            page: Int,
            success: (response: ActorsResponse) -> Unit,
            failure: (t: Throwable) -> Unit
        )

        fun getPopularPeople(page: Int): Single<ActorsResponse>
    }
}