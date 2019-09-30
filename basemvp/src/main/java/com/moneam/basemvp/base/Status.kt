package com.moneam.basemvp.base

import androidx.annotation.IntDef

sealed class Status {
    data class Error(
        val errorCode: ErrorStatus? = null,
        @ErrorStatus2 val errorCode2: Int? = null,
        val errorMessage: String? = null,
        val errorStringResource: Int? = null
    )
}

sealed class ErrorStatus(val errorCode: Int) {
    object NO_INTERNET_CONNECTION : ErrorStatus(5000)
    object UNAUTHORIZED : ErrorStatus(401)
    object DATA_NOT_FOUND : ErrorStatus(404)
}

@Retention(AnnotationRetention.SOURCE)
@IntDef(NO_INTERNET_CONNECTION, UNAUTHORIZED, DATA_NOT_FOUND)
annotation class ErrorStatus2

const val NO_INTERNET_CONNECTION = 5000
const val UNAUTHORIZED = 401
const val DATA_NOT_FOUND = 404
