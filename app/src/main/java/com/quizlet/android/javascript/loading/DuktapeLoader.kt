package com.quizlet.android.javascript.loading

import android.content.Context
import com.quizlet.android.javascript.JsExecutionScheduler
import com.squareup.duktape.Duktape
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable

internal class DuktapeLoader(context: Context) : BaseLoader(context) {
    private val mDuktape: Duktape = Duktape.create()

    override fun execute(listener: (Long) -> Unit) {
        Observable.defer{
                    val startTime = System.nanoTime()
                    mDuktape.evaluate(js)
                    val endTime = System.nanoTime()
                    Observable.just(endTime - startTime)
                }
                .subscribeOn(JsExecutionScheduler.get())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{ durationNs -> listener.invoke(durationNs) }
    }
}