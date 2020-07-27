package com.quizlet.android.javascript.loading

import android.content.Context
import com.eclipsesource.v8.V8
import com.quizlet.android.javascript.JsExecutionScheduler
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable

internal class V8Loader(context: Context) : BaseLoader(context) {
    private lateinit var mV8: V8

    override fun execute(listener: (Long) -> Unit) {
        Observable.defer {
            val startTime = System.nanoTime()
            mV8.executeScript(js)
            val endTime = System.nanoTime()
            Observable.just(endTime - startTime)
        }
                .subscribeOn(JsExecutionScheduler.get())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { durationNs -> listener.invoke(durationNs) }
    }

    init {
        JsExecutionScheduler.get().createWorker()
                .schedule { mV8 = V8.createV8Runtime() }
    }
}