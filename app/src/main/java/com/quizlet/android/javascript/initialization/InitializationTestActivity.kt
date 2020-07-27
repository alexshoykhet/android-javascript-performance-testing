package com.quizlet.android.javascript.initialization

import android.os.Bundle
import com.quizlet.android.javascript.BaseTestActivity
import com.quizlet.android.javascript.Engine
import com.quizlet.android.javascript.Executor

class InitializationTestActivity : BaseTestActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Initialization"
    }

    override fun getExecutor(): Executor {
        return when (engine) {
            Engine.JSEVALUATOR -> JsEvalInitializer(this)
            Engine.ANDROIDJSCORE -> JsCoreInitializer()
            Engine.J2V8 -> V8Initializer()
            Engine.DUKTAPE -> DuktapeInitializer()
            Engine.RHINO -> RhinoInitializer()
        }
    }
}