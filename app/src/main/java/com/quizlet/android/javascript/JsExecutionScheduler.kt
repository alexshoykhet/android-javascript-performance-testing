package com.quizlet.android.javascript

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

object JsExecutionScheduler {
    val INSTANCE = Schedulers.from(
            ThreadPoolExecutor(
                    1, 1,
                    60,
                    TimeUnit.SECONDS,
                    LinkedBlockingQueue(),
                    ThreadFactory { target: Runnable? -> Thread(target) }
            )
    )

    fun get(): Scheduler {
        return INSTANCE
    }
}