package com.sample.android.qapital.sync

import com.firebase.jobdispatcher.Job
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.firebase.jobdispatcher.RetryStrategy
import com.sample.android.qapital.data.source.GoalsRepository
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject

class GoalsFirebaseJobService @Inject constructor(
    private val goalsRepository: GoalsRepository
) : JobService() {

    /**
     * The entry point to your Job. Implementations should offload work to another thread of
     * execution as soon as possible.
     *
     *
     * This is called by the Job Dispatcher to tell us we should start our job. Keep in mind this
     * method is run on the application's main thread, so we need to offload work to a background
     * thread.
     *
     * @return whether there is more work remaining.
     */
    override fun onStartJob(jobParameters: JobParameters): Boolean {
        doAsync {
            goalsRepository.getGoalsFromRemoteDataSource()
            uiThread {
                jobFinished(jobParameters, false)
            }
        }
        return true
    }

    /**
     * Called when the scheduling engine has decided to interrupt the execution of a running job,
     * most likely because the runtime constraints associated with the job are no longer satisfied.
     *
     * @return whether the job should be retried
     * @see Job.Builder.setRetryStrategy
     * @see RetryStrategy
     */
    override fun onStopJob(jobParameters: JobParameters): Boolean {
        return true
    }
}