package com.quizlet.android.javascript.grading

import android.content.Context
import com.quizlet.android.javascript.JsExecutionScheduler
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import org.mozilla.javascript.Scriptable

internal class RhinoGrader(context: Context) : BaseGrader(context) {
    private lateinit var cx: org.mozilla.javascript.Context
    private var scope: Scriptable? = null
    fun init() {
        cx = org.mozilla.javascript.Context.enter()
        cx.setOptimizationLevel(1)
        scope = cx.initStandardObjects()
        val js = "var grader = LearnModeGraderFactory.create();"
        cx.evaluateString(scope, baseJs, "<Grader>", 1, null)
        cx.evaluateString(scope, js, "<Grader>", 1, null)
    }

    override fun execute(listener: (Long) -> Unit) {
        Observable
                .defer {
                    val startTime = System.nanoTime()
                    cx.evaluateString(scope, executionJs, "<Grader>", 1, null)
                    val endTime = System.nanoTime()
                    Observable.just(endTime - startTime)
                }
                .subscribeOn(JsExecutionScheduler.get())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { durationNs -> listener.invoke(durationNs) }
    }

    init {
        JsExecutionScheduler.get().createWorker().schedule { init() }
    }
}