package com.quizlet.android.javascript.loading

import android.content.Context
import com.quizlet.android.javascript.JsExecutionScheduler
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import org.mozilla.javascript.Scriptable

internal class RhinoLoader(context: Context) : BaseLoader(context) {
    private lateinit var cx: org.mozilla.javascript.Context

    override fun execute(listener: (Long) -> Unit) {
        Observable
                .defer {
                    val startTime = System.nanoTime()
                    cx = org.mozilla.javascript.Context.enter()
                    cx.optimizationLevel = -1
                    val scope: Scriptable = cx.initStandardObjects()
                    cx.evaluateString(scope, js, "<cmd>", 1, null)
                    org.mozilla.javascript.Context.exit()
                    val endTime = System.nanoTime()
                    Observable.just(endTime - startTime)
                }
                .subscribeOn(JsExecutionScheduler.get())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { durationNs ->
                    listener.invoke(durationNs)
                }
    }

    init {
        JsExecutionScheduler.get().createWorker()
                .schedule {}
    }
}