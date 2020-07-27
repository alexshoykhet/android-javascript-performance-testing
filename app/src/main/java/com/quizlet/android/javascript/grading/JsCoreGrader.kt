package com.quizlet.android.javascript.grading

import android.content.Context
import android.util.Log
import com.quizlet.android.javascript.JsExecutionScheduler
import com.quizlet.android.javascript.SubmissionContext
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import org.liquidplayer.javascript.JSContext
import org.liquidplayer.javascript.JSObject

internal class JsCoreGrader(context: Context?) : BaseGrader(context!!) {
    private val mGrader: JSObject
    override fun execute(listener: (Long) -> Unit) {
        Observable.defer{
                    val startTime = System.nanoTime()
                    mGrader
                            .property("grade")
                            .toFunction()
                            .call(mGrader, "'foo'", "'bar'", SubmissionContext("en", "en", "Test").toJSON())
                    val endTime = System.nanoTime()
                    Observable.just(endTime - startTime)
                }
                .subscribeOn(JsExecutionScheduler.get())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{ durationNs -> listener.invoke(durationNs) }
    }

    init {
        val jsContext = JSContext()
        jsContext.evaluateScript(baseJs)
        jsContext.setExceptionHandler{ exception ->
            Log.e("JsCoreGrader", exception.getLocalizedMessage())
            exception.printStackTrace()
        }
        mGrader = jsContext
                .evaluateScript("LearnModeGraderFactory.create()")
                .toObject()
    }
}