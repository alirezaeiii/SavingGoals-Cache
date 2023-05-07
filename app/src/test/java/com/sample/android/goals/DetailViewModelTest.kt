package com.sample.android.goals

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sample.android.goals.data.Feed
import com.sample.android.goals.data.SavingsGoal
import com.sample.android.goals.data.SavingsRule
import com.sample.android.goals.data.source.DetailsRepository
import com.sample.android.goals.network.*
import com.sample.android.goals.util.Resource
import com.sample.android.goals.util.formatter.DefaultCurrencyFormatter
import com.sample.android.goals.util.schedulers.BaseSchedulerProvider
import com.sample.android.goals.util.schedulers.ImmediateSchedulerProvider
import com.sample.android.goals.viewmodels.DetailViewModel
import io.reactivex.Observable
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: DetailsRepository
    private lateinit var schedulerProvider: BaseSchedulerProvider

    private lateinit var savingsGoal: SavingsGoal
    private lateinit var feed: Feed
    private lateinit var savingsRule: SavingsRule

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        // Make the sure that all schedulers are immediate.
        schedulerProvider = ImmediateSchedulerProvider()

        savingsGoal = SavingsGoal("", .0f, 12f, "name", 1)
        feed = Feed("id", "type", "timestamp", "msg", 12f)
        savingsRule = SavingsRule(1, "type")
    }

    @Test
    fun loadFeeds() {
        val observableResponse1 = Observable.just((listOf(feed)))
        `when`(repository.getFeeds(anyInt())).thenReturn(observableResponse1)

        val observableResponse2 = Observable.just(listOf(savingsRule))
        `when`(repository.getSavingRules()).thenReturn(observableResponse2)

        val viewModel = DetailViewModel(
            repository,
            schedulerProvider,
            DefaultCurrencyFormatter(Locale.getDefault()),
            savingsGoal
        )

        viewModel.liveData.value.let {
            if (it is Resource.Success) {
                assertFalse(it.data?.feeds.isNullOrEmpty())
                assertTrue(it.data?.feeds?.size == 1)
                assertFalse(it.data?.savingRules.isNullOrEmpty())
            }
        }
    }
}