package com.sample.android.qapital.util

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Executor that runs a goal on a new background thread.
 */
@Singleton
class DiskIOThreadExecutor @Inject constructor() : Executor {

    private val diskIO = Executors.newSingleThreadExecutor()

    override fun execute(command: Runnable) { diskIO.execute(command) }
}