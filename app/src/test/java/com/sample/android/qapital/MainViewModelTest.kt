package com.sample.android.qapital

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.data.source.GoalsRepository
import com.sample.android.qapital.data.source.local.GoalsDao
import com.sample.android.qapital.data.source.local.QapitalLocalDataSource
import com.sample.android.qapital.network.QapitalService
import com.sample.android.qapital.network.SavingsGoalWrapper
import com.sample.android.qapital.util.Resource
import com.sample.android.qapital.util.schedulers.BaseSchedulerProvider
import com.sample.android.qapital.util.schedulers.ImmediateSchedulerProvider
import com.sample.android.qapital.viewmodels.MainViewModel
import io.reactivex.Observable
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var remoteDataSource: QapitalService

    @Mock
    private lateinit var dao: GoalsDao

    private lateinit var schedulerProvider: BaseSchedulerProvider

    private lateinit var localDataSource: QapitalLocalDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        // Make the sure that all schedulers are immediate.
        schedulerProvider = ImmediateSchedulerProvider()

        localDataSource = QapitalLocalDataSource(dao)
    }

    @Test
    fun loadSavingsGoal() {
        val repository = GoalsRepository(remoteDataSource, localDataSource)

        val savingsGoal = SavingsGoal("", .0f, 12f, "name", 1)
        val observableResponse =
            Observable.just(SavingsGoalWrapper(listOf(savingsGoal)))
        `when`(remoteDataSource.requestSavingGoals()).thenReturn(observableResponse)

        val viewModel = MainViewModel(repository, schedulerProvider)

        viewModel.liveData.value.let {
            if (it is Resource.Success) {
                assertFalse(it.data?.wrapper.isNullOrEmpty())
                assertTrue(it.data?.wrapper?.size == 1)
            }
        }
    }

    @Test
    fun errorLoadingSavingsGoal() {
        val repository = GoalsRepository(remoteDataSource, localDataSource)

        val observableResponse = Observable.error<SavingsGoalWrapper>(Exception("error"))
        `when`(remoteDataSource.requestSavingGoals()).thenReturn(observableResponse)

        val viewModel = MainViewModel(repository, schedulerProvider)

        viewModel.liveData.value.let {
            if (it is Resource.Failure) {
                assertTrue(it.cause == "error")
            }
        }
    }
}