package com.quizlet.android.javascript.looping

import android.content.Context
import com.quizlet.android.javascript.Executor
import com.quizlet.android.javascript.IoUtils

internal abstract class BaseLooper : Executor {
    fun getLoopJs(context: Context): String {
        return IoUtils.getJsFromPath(context, JS_LOOP_FILEPATH)
    }

    companion object {
        private const val JS_LOOP_FILEPATH = "js/SimpleLoop.js"
    }
}