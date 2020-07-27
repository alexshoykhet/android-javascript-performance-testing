package com.quizlet.android.javascript.looping

import android.content.Context
import com.eclipsesource.v8.V8
import com.quizlet.android.javascript.JsExecutionScheduler
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable

internal class V8Looper(context: Context) : BaseLooper() {
    private lateinit var mV8: V8

    fun init(context: Context): V8 {
        val v8 = V8.createV8Runtime()
        val baseJs = getLoopJs(context) + "; "
        v8.executeScript(baseJs)
        return v8
    }

    override fun execute(listener: (Long) -> Unit) {
        Observable
                .defer {
                    val startTime = System.nanoTime()
                    mV8.executeIntegerScript("getMax()")
                    val endTime = System.nanoTime()
                    Observable.just(endTime - startTime)
                }
                .subscribeOn(JsExecutionScheduler.get())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { duration ->
                    listener.invoke(duration)
                }
    }

    init {
        JsExecutionScheduler
                .get()
                .createWorker()
                .schedule { mV8 = init(context) }
    }
}