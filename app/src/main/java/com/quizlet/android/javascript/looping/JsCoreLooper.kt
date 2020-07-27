package com.quizlet.android.javascript.looping

import android.content.Context
import com.quizlet.android.javascript.JsExecutionScheduler
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import org.liquidplayer.javascript.JSContext

internal class JsCoreLooper(context: Context) : BaseLooper() {
    private val mJSContext: JSContext = JSContext()

    override fun execute(listener: (Long) -> Unit) {
        Observable
                .defer {
                    val startTime = System.nanoTime()
                    mJSContext.evaluateScript("getMax()")
                    val endTime = System.nanoTime()
                    Observable.just(endTime - startTime)
                }
                .subscribeOn(JsExecutionScheduler.get())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { duration -> listener.invoke(duration) }
    }

    init {
        val baseJs = getLoopJs(context)
        mJSContext.evaluateScript(baseJs)
    }
}