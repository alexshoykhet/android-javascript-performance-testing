package com.quizlet.android.javascript

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import java.util.concurrent.TimeUnit

abstract class BaseTestActivity : AppCompatActivity() {
    @JvmField
    @BindView(R.id.test_option_engine_jsevaluator)
    var mEngineJSEval: RadioButton? = null

    @JvmField
    @BindView(R.id.test_option_engine_jscore)
    var mEngineJsCore: RadioButton? = null

    @JvmField
    @BindView(R.id.test_option_engine_v8)
    var mEngineV8: RadioButton? = null

    @JvmField
    @BindView(R.id.test_option_engine_duktape)
    var mEngineDuktape: RadioButton? = null

    @JvmField
    @BindView(R.id.test_option_engine_rhino)
    var mEngineRhino: RadioButton? = null

    @JvmField
    @BindView(R.id.test_option_iterations)
    var mIterationsEditText: EditText? = null

    @JvmField
    @BindView(R.id.start_test)
    var mStartTest: View? = null

    @JvmField
    @BindView(R.id.test_status)
    var mTestStatus: TextView? = null

    @JvmField
    @BindView(R.id.test_result)
    var mTestResult: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_activity)
        ButterKnife.bind(this)
        iterations = DEFAULT_ITERATIONS
    }

    @OnClick(R.id.start_test)
    fun onStartClicked() {
        setTestStatus("")
        setTestResult("")
        setStartButtonEnabled(false)
        val text = mIterationsEditText!!.text.toString()
        var iterations = -1
        try {
            iterations = text.toInt()
        } catch (exception: NumberFormatException) {
            Toast.makeText(this, R.string.error_invalid_input, Toast.LENGTH_SHORT).show()
        }
        if (iterations < 0 || iterations > MAX_ITERATIONS) {
            iterations = DEFAULT_ITERATIONS
        }
        iterations = iterations
        test()
    }

    protected fun setTestStatus(message: String?) {
        mTestStatus!!.text = message
    }

    protected fun setTestResult(message: String?) {
        mTestResult!!.text = message
    }

    protected fun setStartButtonEnabled(enable: Boolean) {
        mStartTest!!.isEnabled = enable
    }

    protected var iterations: Int
        protected get() = mIterationsEditText!!.text.toString().toInt()
        protected set(num) {
            mIterationsEditText!!.setText(num.toString())
        }

    protected val engine: Engine
        protected get() {
            return when {
                mEngineJSEval!!.isChecked -> Engine.JSEVALUATOR
                mEngineJsCore!!.isChecked -> Engine.ANDROIDJSCORE
                mEngineV8!!.isChecked -> Engine.J2V8
                mEngineDuktape!!.isChecked -> Engine.DUKTAPE
                mEngineRhino!!.isChecked -> Engine.RHINO
                else -> throw IllegalStateException("Pick a Javascript engine")
            }
        }

    @SuppressLint("SetTextI18n")
    protected fun showTime(durationNs: Long) {
        val timeTakenMs = TimeUnit.MILLISECONDS.convert(durationNs, TimeUnit.NANOSECONDS)
        mTestStatus!!.text = "Completed"
        mTestResult!!.text = String.format("%s ms", timeTakenMs)
    }

    private fun test() {
        setTestStatus("Running test...")
        val grader = getExecutor()
        val iterations = iterations
        testHelper(grader, 0, 0, iterations)
    }

    fun testHelper(grader: Executor, cumulativeRunningTimeNs: Long, count: Int, total: Int) {
        if (count >= total) {
            testFinished(cumulativeRunningTimeNs)
        } else {
            grader.execute { durationNs: Long ->
                setTestStatus("Completed: $count")
                testHelper(grader, cumulativeRunningTimeNs + durationNs, count + 1, total)
            }
        }
    }

    private fun testFinished(durationNs: Long) {
        showTime(durationNs)
        setStartButtonEnabled(true)
    }

    protected abstract fun getExecutor(): Executor

    companion object {
        const val DEFAULT_ITERATIONS = 100
        const val MAX_ITERATIONS = 65536
    }
}