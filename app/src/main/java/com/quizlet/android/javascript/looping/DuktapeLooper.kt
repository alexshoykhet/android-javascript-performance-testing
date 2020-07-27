package com.quizlet.android.javascript.looping

import android.content.Context
import com.quizlet.android.javascript.JsExecutionScheduler
import com.squareup.duktape.Duktape
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable

internal class DuktapeLooper(context: Context) : BaseLooper() {
    private val mDuktape: Duktape
    fun init(context: Context): Duktape {
        val duktape = Duktape.create()
        val baseJs = getLoopJs(context) + "; "
        duktape.evaluate(baseJs)
        return duktape
    }

    override fun execute(listener: (Long) -> Unit) {
        Observable
                .defer {
                    val startTime = System.nanoTime()
                    mDuktape.evaluate("getMax()")
                    val endTime = System.nanoTime()
                    Observable.just(endTime - startTime)
                }
                .subscribeOn(JsExecutionScheduler.get())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { duration -> listener.invoke(duration) }
    }

    init {
        mDuktape = init(context)
    }
}