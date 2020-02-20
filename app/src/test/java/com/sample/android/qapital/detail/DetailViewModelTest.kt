package com.sample.android.qapital.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sample.android.qapital.api.QapitalApi
import com.sample.android.qapital.data.Feed
import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.data.SavingsRule
import com.sample.android.qapital.viewmodels.DetailViewModel
import com.sample.android.qapital.util.schedulers.BaseSchedulerProvider
import com.sample.android.qapital.util.schedulers.ImmediateSchedulerProvider
import com.sample.android.qapital.data.usecase.DetailUseCase
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

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var api: QapitalApi
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
        savingsRule = SavingsRule(1, "type", 12f)
    }

    @Test
    fun loadFeeds() {
        val observableResponse1 = Observable.just(QapitalApi.FeedWrapper(listOf(feed)))
        `when`(api.requestFeeds(anyInt())).thenReturn(observableResponse1)

        val observableResponse2 =
            Observable.just(QapitalApi.SavingsRuleWrapper(listOf(savingsRule)))
        `when`(api.requestSavingRules()).thenReturn(observableResponse2)

        val useCase = DetailUseCase(schedulerProvider, api)
        val viewModel = DetailViewModel(useCase, savingsGoal)

        with(viewModel) {
            assertFalse(feeds.value!!.isEmpty())
            assertTrue(feeds.value!!.size == 1)
            assertFalse(savingsRules.value.isNullOrEmpty())
        }
    }
}