package com.quizlet.android.javascript.looping

import android.content.Context
import com.evgenii.jsevaluator.JsEvaluator
import com.evgenii.jsevaluator.interfaces.JsCallback

internal class JsEvalLooper(context: Context) : BaseLooper() {
    private val mJsEvaluator: JsEvaluator = JsEvaluator(context)
    private val mJs: String = getLoopJs(context)

    override fun execute(listener: (Long) -> Unit) {
        val startTime = System.nanoTime()
        val callback: JsCallback = object : JsCallback {
            override fun onResult(result: String?) {
                val endTime = System.nanoTime()
                listener.invoke(endTime - startTime)
            }

            override fun onError(p0: String?) {
                TODO("Not yet implemented")
            }
        }

        mJsEvaluator.evaluate("$mJs; getMax()", callback)
    }
}