package com.quizlet.android.javascript.loading

import android.content.Context
import com.quizlet.android.javascript.Executor
import com.quizlet.android.javascript.IoUtils

internal abstract class BaseLoader(context: Context) : Executor {
    protected val js: String
    fun loadJs(context: Context): String {
        return "var list = " + IoUtils.getJsFromPath(context, JS_FILEPATH)
    }

    companion object {
        private const val JS_FILEPATH = "js/MockData.json"
    }

    init {
        js = loadJs(context)
    }
}