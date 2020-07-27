package com.quizlet.android.javascript

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.OnClick
import com.quizlet.android.javascript.grading.GradingActivity
import com.quizlet.android.javascript.initialization.InitializationTestActivity
import com.quizlet.android.javascript.loading.LoadingTestActivity
import com.quizlet.android.javascript.looping.LoopingTestActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        ButterKnife.bind(this)
    }

    @OnClick(R.id.test_initialization)
    fun onInitializationTestClicked() {
        startActivity(Intent(this, InitializationTestActivity::class.java))
    }

    @OnClick(R.id.test_execution_loop)
    fun onLoopingTestClicked() {
        startActivity(Intent(this, LoopingTestActivity::class.java))
    }

    @OnClick(R.id.test_execution_load)
    fun onLoadingTestClicked() {
        startActivity(Intent(this, LoadingTestActivity::class.java))
    }

    @OnClick(R.id.test_grader)
    fun onGradingTestClicked() {
        startActivity(Intent(this, GradingActivity::class.java))
    }
}