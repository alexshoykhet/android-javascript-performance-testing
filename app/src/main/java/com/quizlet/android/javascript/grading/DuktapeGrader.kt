package com.quizlet.android.javascript.grading

import android.content.Context
import com.quizlet.android.javascript.JsExecutionScheduler
import com.squareup.duktape.Duktape
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable

internal class DuktapeGrader(context: Context) : BaseGrader(context) {
    private val mDuktape: Duktape
    fun init(): Duktape {
        val duktape = Duktape.create()
        duktape.evaluate(baseJs)
        duktape.evaluate("var grader = LearnModeGraderFactory.create();")
        return duktape
    }

    override fun execute(listener: (Long) -> Unit) {
        Observable
                .defer {
                    val startTime = System.nanoTime()
                    mDuktape.evaluate(executionJs)
                    val endTime = System.nanoTime()
                    Observable.just(endTime - startTime)
                }
                .subscribeOn(JsExecutionScheduler.get())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { durationNs -> listener.invoke(durationNs) }
    }

    init {
        mDuktape = init()
    }
}