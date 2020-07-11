package com.snatap.myway.utils.common

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

object RxEditText {
    fun getTextWatcherObservable(editText: EditText): Observable<String> {
        val subject = PublishSubject.create<String>()
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                subject.onNext(s.toString())
            }
        })
        return subject
    }


}
