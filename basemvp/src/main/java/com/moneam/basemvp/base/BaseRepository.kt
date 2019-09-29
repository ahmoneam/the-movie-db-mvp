package com.moneam.basemvp.base

abstract class BaseRepository : BaseContract.BaseIRepository {
    val remoteDataSource = RemoteDataSource.Instance
    val localDataSource = LocalDataSource.Instance
}

