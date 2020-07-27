package com.quizlet.android.javascript.initialization

import android.content.Context
import com.evgenii.jsevaluator.JsEvaluator
import com.quizlet.android.javascript.Executor

internal class JsEvalInitializer(val mContext: Context) : Executor {
    override fun execute(listener: (Long) -> Unit) {
        val startTime = System.nanoTime()
        val evaluator = JsEvaluator(mContext)
        val endTime = System.nanoTime()
        evaluator.destroy()
        listener.invoke(endTime - startTime)
    }
}