package com.quizlet.android.javascript.loading

import android.os.Bundle
import com.quizlet.android.javascript.BaseTestActivity
import com.quizlet.android.javascript.Engine
import com.quizlet.android.javascript.Executor

class LoadingTestActivity : BaseTestActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Loading"
    }

    override fun getExecutor(): Executor {
        return when (engine) {
            Engine.JSEVALUATOR -> JsEvalLoader(this)
            Engine.ANDROIDJSCORE -> JsCoreLoader(this)
            Engine.J2V8 -> V8Loader(this)
            Engine.DUKTAPE -> DuktapeLoader(this)
            Engine.RHINO -> RhinoLoader(this)
        }
    }
}