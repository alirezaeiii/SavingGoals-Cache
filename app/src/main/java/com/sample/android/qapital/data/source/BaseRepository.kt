package com.sample.android.qapital.data.source

import io.reactivex.Observable

abstract class BaseRepository<T> {

    protected var cacheIsDirty = false

    protected abstract val resultRemoteDataSource: Observable<T>

    protected abstract val resultLocalDataSource: Observable<T>

    val result: Observable<T> = Observable.fromCallable { cacheIsDirty }.flatMap {
        if (it) {
            resultRemoteDataSource
        } else {
            resultLocalDataSource.onErrorResumeNext(resultRemoteDataSource)
        }
    }

    fun refresh() {
        cacheIsDirty = true
    }
}