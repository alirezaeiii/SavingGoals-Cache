package com.sample.android.qapital.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sample.android.qapital.network.QapitalService
import com.sample.android.qapital.data.Feed
import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.data.SavingsRule
import com.sample.android.qapital.util.CurrencyFormatterFraction
import com.sample.android.qapital.util.Resource
import com.sample.android.qapital.util.schedulers.BaseSchedulerProvider
import com.sample.android.qapital.util.schedulers.ImmediateSchedulerProvider
import com.sample.android.qapital.viewmodels.DetailViewModel
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
import org.mockito.Mockito.anyInt
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var api: QapitalService
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
        val observableResponse1 = Observable.just(QapitalService.FeedWrapper(listOf(feed)))
        `when`(api.requestFeeds(anyInt())).thenReturn(observableResponse1)

        val observableResponse2 =
            Observable.just(QapitalService.SavingsRuleWrapper(listOf(savingsRule)))
        `when`(api.requestSavingRules()).thenReturn(observableResponse2)

        val viewModel = DetailViewModel(
            api,
            schedulerProvider,
            CurrencyFormatterFraction(Locale.getDefault()),
            savingsGoal
        )

        with(viewModel.feeds.value) {
            if (this is Resource.Success) {
                assertFalse(data!!.isEmpty())
                assertTrue(data!!.size == 1)
            }
        }
        with(viewModel.savingsRules.value) {
            if (this is Resource.Success) {
                assertFalse(data.isNullOrEmpty())
            }
        }
    }
}