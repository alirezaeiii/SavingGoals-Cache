package com.sample.android.qapital.ui.goals

import android.os.Bundle
import com.sample.android.qapital.R
import com.sample.android.qapital.util.addFragmentToActivity
import com.sample.android.qapital.util.setupActionBar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_savings_goals.*
import javax.inject.Inject

class SavingsGoalsActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var fragment: SavingsGoalsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_savings_goals)

        setupActionBar(toolbar) {
            setTitle(R.string.savings_goals)
        }

        if (savedInstanceState == null) {

            supportFragmentManager.findFragmentById(R.id.fragment_container)
                    as SavingsGoalsFragment? ?: fragment.also {
                addFragmentToActivity(it, R.id.fragment_container)
            }
        }
    }
}
