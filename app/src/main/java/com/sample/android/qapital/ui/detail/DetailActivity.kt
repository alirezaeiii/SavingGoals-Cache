package com.sample.android.qapital.ui.detail

import android.os.Bundle
import android.view.MenuItem
import com.sample.android.qapital.R
import com.sample.android.qapital.util.addFragmentToActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class DetailActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var fragment : DetailFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (savedInstanceState == null) {
            supportFragmentManager.findFragmentById(R.id.fragment_container)
                    as DetailFragment? ?: fragment.also {
                addFragmentToActivity(it, R.id.fragment_container)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        const val EXTRA_SAVINGS_GOAL = "savingsGoal"
    }
}
