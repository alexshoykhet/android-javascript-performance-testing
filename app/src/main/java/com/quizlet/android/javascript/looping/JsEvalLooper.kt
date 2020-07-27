package com.quizlet.android.javascript.looping

import android.content.Context
import com.evgenii.jsevaluator.JsEvaluator

internal class JsEvalLooper(context: Context) : BaseLooper() {
    private val mJsEvaluator: JsEvaluator = JsEvaluator(context)
    private val mJs: String = getLoopJs(context)

    override fun execute(listener: (Long) -> Unit) {
        val startTime = System.nanoTime()
        mJsEvaluator.evaluate("$mJs; getMax()") { result: String? ->
            val endTime = System.nanoTime()
            listener.invoke(endTime - startTime)
        }
    }
}