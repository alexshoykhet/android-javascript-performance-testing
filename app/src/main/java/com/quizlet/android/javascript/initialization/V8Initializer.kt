package com.quizlet.android.javascript.initialization

import com.eclipsesource.v8.V8
import com.quizlet.android.javascript.Executor
import com.quizlet.android.javascript.JsExecutionScheduler
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable

internal class V8Initializer : Executor {
    override fun execute(listener: (Long) -> Unit) {
        Observable
                .defer {
                    val startTime = System.nanoTime()
                    val runtime = V8.createV8Runtime()
                    val endTime = System.nanoTime()
                    runtime.release()
                    Observable.just(endTime - startTime)
                }

                .subscribeOn(JsExecutionScheduler.get())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { durationNs -> listener.invoke(durationNs) }
    }
}