package com.quizlet.android.javascript.grading

import android.os.Bundle
import com.quizlet.android.javascript.BaseTestActivity
import com.quizlet.android.javascript.Engine
import com.quizlet.android.javascript.Executor

class GradingActivity : BaseTestActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Grading"
    }

    override fun getExecutor(): Executor {
        return when (engine) {
            Engine.JSEVALUATOR -> JsEvalGrader(this)
            Engine.ANDROIDJSCORE -> JsCoreGrader(this)
            Engine.J2V8 -> V8Grader(this)
            Engine.DUKTAPE -> DuktapeGrader(this)
            Engine.RHINO -> RhinoGrader(this)
        }
    }
}