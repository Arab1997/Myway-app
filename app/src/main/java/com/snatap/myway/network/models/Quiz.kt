package com.snatap.myway.network.models

data class QuizResp(
    val quiz: Quiz,
    val success: Boolean
)

data class Quiz(
    val created_at: String,
    val id: Int,
    val questions: List<Question>,
    val reward: Int,
    val title: String,
    val updated_at: String
)

data class Question(
    val answer_options: Any,
    val answers: List<Answer>,
    val created_at: String,
    val id: Int,
    val order: Int,
    val quiz_id: Int,
    val text: String,
    val type: String,
    val updated_at: String
)

data class Answer(
    val created_at: String,
    val id: Int,
    val order: Int,
    val quiz_question_id: Int,
    val text: String,
    val updated_at: String
)


data class QuizAnswerRequest(
    val question_id: Int,
    val answer_id: Int? = null,
    val answer_text: String? = null
)

