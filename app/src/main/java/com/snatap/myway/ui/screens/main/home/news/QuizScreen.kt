package com.snatap.myway.ui.screens.main.home.news

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.Question
import com.snatap.myway.network.models.QuizAnswerRequest
import com.snatap.myway.network.models.SuccessResp
import com.snatap.myway.utils.Constants
import com.snatap.myway.utils.extensions.*
import kotlinx.android.synthetic.main.content_rounded_toolbar_quiz.*
import kotlinx.android.synthetic.main.screen_quizes.*

class QuizScreen : BaseFragment(R.layout.screen_quizes) {

    companion object {
        fun newInstance(currentPos: Int): QuizScreen {
            return QuizScreen().apply { setPos(currentPos) }
        }
    }

    private var currentPos: Int = 0
    private fun setPos(currentPos: Int) {
        this.currentPos = currentPos
    }

    private var request = false
    private var answers = arrayListOf<QuizAnswerRequest>()
    private lateinit var currentQuestion: Question

    @SuppressLint("SetTextI18n")
    override fun initialize() {

        currentQuestion = NewsDetailScreen.quiz.quiz.questions[currentPos]
        currentQuestion.let {
            position.text = "${currentPos + 1} из ${NewsDetailScreen.quiz.quiz.questions.size}"
            question.text = it.text
            when (it.type) {
                Constants.RADIO -> {
                    radio.visible()
                    radio.setItems(currentQuestion.answers.map { it.text })
                }
                Constants.CHECKBOX -> {
                    checkbox.visible()
                    checkbox.setItems(currentQuestion.answers.map { it.text })
                }
                Constants.TEXT -> answer.visible()
                else -> {
                }
            }
        }

        radio.setCheckedItemsListener { next.enable() }
        checkbox.setCheckedItemsListener { next.enable() }
        answer.onTextChanged { next.enableDisable(it.isNotEmpty()) }
        windowAdjustResize()

        close.setOnClickListener { finishFragment() }

        next.disable()

        if (currentPos == NewsDetailScreen.quiz.quiz.questions.lastIndex)
            next.text = "завершить"

        next.setOnClickListener {
            it.blockClickable()
            when (currentQuestion.type) {
                Constants.RADIO -> {
                    val selectedAnswer = radio.getCheckedItems().first().name
                    val id = currentQuestion.answers.first { it.text == selectedAnswer }.id
                    answers = arrayListOf(QuizAnswerRequest(currentQuestion.id, answer_id = id))
                }
                Constants.CHECKBOX -> {
                    val ids = arrayListOf<Int>()
                    currentQuestion.answers.forEach {
                        radio.getCheckedItems().forEach { answer ->
                            if (it.text == answer.name) ids.add(it.id)
                        }
                    }
                    answers = ArrayList(ids.map {
                        QuizAnswerRequest(currentQuestion.id, answer_id = it)
                    })
                }
                Constants.TEXT -> {
                    answers = arrayListOf(
                        QuizAnswerRequest(currentQuestion.id, answer_text = answer.getText())
                    )
                }
                else -> {
                }
            }
            request = true
            showProgress(true)
            viewModel.sendQuizAnswers(NewsDetailScreen.quiz.quiz.id, answers)
        }
    }

    override fun observe() {
        viewModel.data.observe(viewLifecycleOwner, Observer {
            if (request && it is SuccessResp) {
                request = false
                showProgress(false)
                if (currentPos == NewsDetailScreen.quiz.quiz.questions.lastIndex)
                    popInclusive(Constants.NEWS_DETAILED_FRAGMENT)
                else addFragment(newInstance(currentPos + 1))
            }
        })
    }
}