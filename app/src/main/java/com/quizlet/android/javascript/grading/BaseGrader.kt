package com.quizlet.android.javascript.grading

import android.content.Context
import android.util.Log
import com.quizlet.android.javascript.Executor
import com.quizlet.android.javascript.IoUtils
import com.quizlet.android.javascript.SubmissionContext
import java.io.IOException

internal abstract class BaseGrader(private val mContext: Context) : Executor {
    val baseJs: String?
    private fun loadJs(): String? {
        var js: String? = null
        try {
            val open = mContext.assets.open(JS_FILEPATH_GRADER)
            js = IoUtils.readInputStream(open)
        } catch (e: IOException) {
            Log.e("BaseGrader", e.localizedMessage)
        }
        return js
    }

    companion object {
        private const val JS_FILEPATH_GRADER = "js/LearnModeGraderFactory.js"
        val executionJs = String.format(
                "grader.grade(%s, %s, %s)",
                "'foo'",
                "'bar'",
                SubmissionContext("en", "en", "Test").toJSON())
    }

    init {
        baseJs = loadJs()
    }
}