package com.quizlet.android.javascript

class SubmissionContext(var answerLanguageCode: String, var promptLanguageCode: String, var promptText: String) {
    fun toJSON(): String {
        return String.format(
                SUBMISSION_CONTEXT_TEMPLATE,
                answerLanguageCode, promptLanguageCode, promptText)
    }

    companion object {
        const val SUBMISSION_CONTEXT_TEMPLATE = "{'answerLanguage': '%s', 'promptLanguage': '%s', 'promptText': '%s'}"
    }

}