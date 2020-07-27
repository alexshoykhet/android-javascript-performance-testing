package com.quizlet.android.javascript.looping

import android.content.Context
import com.quizlet.android.javascript.JsExecutionScheduler
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import org.mozilla.javascript.Scriptable

internal class RhinoLooper(context: Context) : BaseLooper() {
    private lateinit var cx: org.mozilla.javascript.Context
    private lateinit var scope: Scriptable

    fun init(context: Context) {
        cx = org.mozilla.javascript.Context.enter()
        cx.optimizationLevel = -1
        scope = cx.initStandardObjects()
        cx.evaluateString(scope, getLoopJs(context), "<looper>", 1, null)
    }

    override fun execute(listener: (Long) -> Unit) {
        Observable
                .defer {
                    val startTime = System.nanoTime()
                    cx.evaluateString(scope, "getMax()", "<looper>", 1, null)
                    val endTime = System.nanoTime()
                    Observable.just(endTime - startTime)
                }
                .subscribeOn(JsExecutionScheduler.get())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { duration -> listener.invoke(duration) }
    }

    init {
        JsExecutionScheduler.get().createWorker().schedule { init(context) }
    }
}