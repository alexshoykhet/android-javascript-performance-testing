package com.quizlet.android.javascript.looping

import android.os.Bundle
import com.quizlet.android.javascript.BaseTestActivity
import com.quizlet.android.javascript.Engine
import com.quizlet.android.javascript.Executor

class LoopingTestActivity : BaseTestActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Looping"
    }

    override fun getExecutor(): Executor {
        return when (engine) {
            Engine.JSEVALUATOR -> JsEvalLooper(this)
            Engine.ANDROIDJSCORE -> JsCoreLooper(this)
            Engine.J2V8 -> V8Looper(this)
            Engine.DUKTAPE -> DuktapeLooper(this)
            Engine.RHINO -> RhinoLooper(this)
        }
    }
}