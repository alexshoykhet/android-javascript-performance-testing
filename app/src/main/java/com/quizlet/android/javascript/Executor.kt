package com.quizlet.android.javascript

interface Executor {
    fun execute(listener: (Long) -> Unit)
}