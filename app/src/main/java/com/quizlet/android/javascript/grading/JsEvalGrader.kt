package com.quizlet.android.javascript.grading

import android.content.Context
import com.evgenii.jsevaluator.JsEvaluator

internal class JsEvalGrader(context: Context) : BaseGrader(context) {
    private val mJsEvaluator: JsEvaluator = JsEvaluator(context)

    override fun execute(listener: (Long) -> Unit) {
        // JS Evaluator executes every command in a separate context
        // so we have to load the full script on each iteration
        val js = (baseJs
                + "; var grader = LearnModeGraderFactory.create(); "
                + Companion.executionJs)
        val startTime = System.nanoTime()
        mJsEvaluator.evaluate(js) { result: String? ->
            val endTime = System.nanoTime()
            listener.invoke(endTime - startTime)
        }
    }
}