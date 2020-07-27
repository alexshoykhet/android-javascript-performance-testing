package com.quizlet.android.javascript.grading

import android.content.Context
import com.evgenii.jsevaluator.JsEvaluator
import com.evgenii.jsevaluator.interfaces.JsCallback

internal class JsEvalGrader(context: Context) : BaseGrader(context) {
    private val mJsEvaluator: JsEvaluator = JsEvaluator(context)

    override fun execute(listener: (Long) -> Unit) {
        // JS Evaluator executes every command in a separate context
        // so we have to load the full script on each iteration
        val js = (baseJs
                + "; var grader = LearnModeGraderFactory.create(); "
                + Companion.executionJs)
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

        mJsEvaluator.evaluate(js, callback)
    }
}