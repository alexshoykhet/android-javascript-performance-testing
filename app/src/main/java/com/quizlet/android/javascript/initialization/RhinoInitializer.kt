package com.quizlet.android.javascript.initialization

import com.quizlet.android.javascript.Executor
import com.quizlet.android.javascript.JsExecutionScheduler
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

internal class RhinoInitializer : Executor {
    override fun execute(listener: (Long) -> Unit) {
        Observable
                .defer {
                    val startTime = System.nanoTime()
                    val cx = Context.enter()
                    cx.optimizationLevel = -1
                    val scope: Scriptable = cx.initStandardObjects()
                    Context.exit()
                    val endTime = System.nanoTime()
                    Observable.just(endTime - startTime)
                }
                .subscribeOn(JsExecutionScheduler.get())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { durationNs -> listener.invoke(durationNs) }
    }
}