package com.snatap.myway.base

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.snatap.myway.R
import com.snatap.myway.network.ErrorResp
import com.snatap.myway.utils.extensions.loge
import com.snatap.myway.utils.extensions.logi
import com.snatap.myway.utils.extensions.toast
import com.snatap.myway.utils.network.Errors
import com.snatap.myway.utils.preferences.SharedManager
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named
import retrofit2.HttpException

open class BaseViewModel(
    private val gson: Gson,
    private val context: Context,
    val sharedManager: SharedManager
) : ViewModel(),
    KoinComponent {

    @LayoutRes
    var parentLayoutId: Int = 0

    @LayoutRes
    var navLayoutId: Int = 0

    val data: MutableLiveData<Any> by inject()
    val shared: MutableLiveData<Any> by inject(named("sharedLive"))
    val error: MutableLiveData<ErrorResp> by inject(named("errorLive"))

    private val compositeDisposable = CompositeDisposable()

    private fun parseError(e: Throwable?) {
        var message = context.resources.getString(R.string.smth_wrong)
        if (e != null && e.localizedMessage != null) {
            loge(e.localizedMessage)
            if (e is HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                errorBody?.let {
                    try {
                        loge(it)
                        val errors = it.split(":")
                            .filter { it.contains("[") }
                        val errorsString = if (errors.isNotEmpty()) {
                            errors.toString()
                                .replace("[", "")
                                .replace(",", "\n")
                                .replace("]", "")
                                .replace("{", "")
                                .replace("}", "")
                                .replace("\"", "")
                        } else {
                            val resp = it.split(":")
                            if (resp.size >= 2) resp[1].replace("{", "")
                                .replace("}", "")
                                .replace("\"", "")
                            else it
                        }

                        message = if (errorsString.isEmpty())
                            context.resources.getString(R.string.smth_wrong)
                        else errorsString

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } else message = Errors.traceErrors(e, context)
        }
        toast(context, message)
        error.value = ErrorResp(message)
    }


    fun fetchData() {

        if (sharedManager.token.isNotEmpty()) {

            logi("Current token : " + sharedManager.token)

        }
    }

    private fun getToken() = "Bearer ${sharedManager.token}"
/*

    fun checkSuggestion(address: String) = compositeDisposable.add(
        dadataApi.checkSuggestion(
            DaDataRequest(
                10,
                FromBound("street"),
                arrayListOf(Location("москва")),
                address,
                true
            )
        ).observeAndSubscribe()
            .subscribe({
                data.value = it
            }, {
                parseError(it)
            })
    )
*/

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

fun <T> Single<T>.observeAndSubscribe() =
    subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
