package com.quizlet.android.javascript

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

object IoUtils {
    fun getJsFromPath(context: Context, path: String): String {
        val open = context.assets.open(path)
        return readInputStream(open)
    }

    @Throws(IOException::class)
    fun readInputStream(ins: InputStream?): String {
        val streamReader = BufferedReader(InputStreamReader(ins))
        val stringBuilder = StringBuilder()
        var inputString: String?
        while (streamReader.readLine().also { inputString = it } != null) {
            stringBuilder.append(inputString)
        }
        return stringBuilder.toString()
    }
}