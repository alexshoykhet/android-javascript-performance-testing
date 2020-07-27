package com.quizlet.android.javascript.grading

import android.content.Context
import com.eclipsesource.v8.V8
import com.quizlet.android.javascript.JsExecutionScheduler
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable

internal class V8Grader(context: Context) : BaseGrader(context) {
    private lateinit var mV8: V8
    fun init(): V8 {
        val v8 = V8.createV8Runtime()
        v8.executeScript(baseJs)
        v8.executeScript("var grader = LearnModeGraderFactory.create();")
        return v8
    }

    override fun execute(listener: (Long) -> Unit) {
        Observable
                .defer {
                    val startTime = System.nanoTime()
                    mV8.executeBooleanScript(executionJs)
                    val endTime = System.nanoTime()
                    Observable.just(endTime - startTime)
                }
                .subscribeOn(JsExecutionScheduler.get())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { durationNs: Long ->
                    listener.invoke(durationNs)
                }
    }

    init {
        JsExecutionScheduler.get().createWorker()
                .schedule { mV8 = init() }
    }
}