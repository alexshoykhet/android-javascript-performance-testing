package com.quizlet.android.javascript.initialization

import com.quizlet.android.javascript.Executor
import com.quizlet.android.javascript.JsExecutionScheduler
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import org.liquidplayer.javascript.JSContext

internal class JsCoreInitializer : Executor {
    override fun execute(listener: (Long) -> Unit) {
        Observable
                .defer {
                    val startTime = System.nanoTime()
                    val jsContext = JSContext()
                    val endTime = System.nanoTime()
                    //TODO - there's no more jsContext.garbageCollect method - how to release the context?
                    Observable.just(endTime - startTime)
                }
                .subscribeOn(JsExecutionScheduler.get())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { durationNs -> listener.invoke(durationNs) }
    }
}