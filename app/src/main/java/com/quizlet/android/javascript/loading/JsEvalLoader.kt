package com.quizlet.android.javascript.loading

import android.content.Context
import com.evgenii.jsevaluator.JsEvaluator

internal class JsEvalLoader(context: Context?) : BaseLoader(context!!) {
    private val mJsEvaluator: JsEvaluator = JsEvaluator(context)
    override fun execute(listener: (Long) -> Unit) {
        val startTime = System.nanoTime()
        mJsEvaluator.evaluate(js) { result: String? ->
            val endTime = System.nanoTime()
            listener.invoke(endTime - startTime)
        }
    }
}