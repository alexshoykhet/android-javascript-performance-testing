package com.quizlet.android.javascript.initialization

import com.quizlet.android.javascript.Executor
import com.quizlet.android.javascript.JsExecutionScheduler
import com.squareup.duktape.Duktape
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable

class DuktapeInitializer : Executor {
    override fun execute(listener: (Long) -> Unit) {
        Observable
                .defer {
                    val startTime = System.nanoTime()
                    val duktape = Duktape.create()
                    val endTime = System.nanoTime()
                    duktape.close()
                    Observable.just(endTime - startTime)
                }
                .subscribeOn(JsExecutionScheduler.get())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { durationNs -> listener.invoke(durationNs) }
    }
}