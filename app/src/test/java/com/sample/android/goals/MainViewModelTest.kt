package com.sample.android.goals

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sample.android.goals.repository.GoalsRepository
import com.sample.android.goals.data.source.local.GoalsDao
import com.sample.android.goals.data.source.local.LocalDataSourceImpl
import com.sample.android.goals.network.ApiService
import com.sample.android.goals.network.SavingsGoalResponse
import com.sample.android.goals.network.SavingsGoalWrapperResponse
import com.sample.android.goals.util.Resource
import com.sample.android.goals.util.schedulers.BaseSchedulerProvider
import com.sample.android.goals.util.schedulers.ImmediateSchedulerProvider
import com.sample.android.goals.viewmodels.MainViewModel
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
    private lateinit var remoteDataSource: ApiService

    @Mock
    private lateinit var dao: GoalsDao

    private lateinit var schedulerProvider: BaseSchedulerProvider

    private lateinit var localDataSourceImpl: LocalDataSourceImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        // Make the sure that all schedulers are immediate.
        schedulerProvider = ImmediateSchedulerProvider()

        localDataSourceImpl = LocalDataSourceImpl(dao)
    }

    @Test
    fun loadSavingsGoal() {
        val repository = GoalsRepository(remoteDataSource, localDataSourceImpl)

        val savingsGoal = SavingsGoalResponse("", .0f, 12f, "name", 1)
        val observableResponse =
            Observable.just(SavingsGoalWrapperResponse(listOf(savingsGoal)))
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
        val repository = GoalsRepository(remoteDataSource, localDataSourceImpl)

        val observableResponse = Observable.error<SavingsGoalWrapperResponse>(Exception("error"))
        `when`(remoteDataSource.requestSavingGoals()).thenReturn(observableResponse)

        val viewModel = MainViewModel(repository, schedulerProvider)

        viewModel.liveData.value.let {
            if (it is Resource.Failure) {
                assertTrue(it.cause == "error")
            }
        }
    }
}