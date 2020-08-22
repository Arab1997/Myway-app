package com.snatap.myway.base

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.LayoutRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.snatap.myway.R
import com.snatap.myway.network.*
import com.snatap.myway.network.models.*
import com.snatap.myway.utils.Constants
import com.snatap.myway.utils.extensions.loge
import com.snatap.myway.utils.extensions.logi
import com.snatap.myway.utils.extensions.toast
import com.snatap.myway.utils.network.Errors
import com.snatap.myway.utils.preferences.SharedManager
import io.reactivex.Observable
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
) : ViewModel(), KoinComponent {

    @LayoutRes
    var parentLayoutId: Int = 0

    @LayoutRes
    var navLayoutId: Int = 0

    val data: MutableLiveData<Any> by inject()
    val shared: MutableLiveData<Any> by inject(named("sharedLive"))
    val error: MutableLiveData<ErrorResp> by inject(named("errorLive"))

    val user: MutableLiveData<User> by inject(named("user"))
    val news: MutableLiveData<ArrayList<News>> by inject(named("news"))
    val chats: MutableLiveData<ArrayList<Chats>> by inject(named("chats"))
    val comments: MutableLiveData<ArrayList<Comment>> by inject(named("comments"))
    val notifications: MutableLiveData<ArrayList<Notification>> by inject(named("notifications"))
    val achievements: MutableLiveData<ArrayList<UserAchievement>> by inject(named("achievements"))
    val streamsMessages: MutableLiveData<ArrayList<StreamMessage>> by inject(named("stream_messages"))
    val streams: MutableLiveData<ArrayList<Stream>> by inject(named("streams"))
    val lessonsDay: MutableLiveData<ArrayList<LessonDay>> by inject(named("lessonsDay"))
    val stores: MutableLiveData<ArrayList<Store>> by inject(named("stores"))
    val sharedStore: MutableLiveData<ArrayList<Store>> by inject(named("stores"))

    private val api = RetrofitClient
        .getRetrofit(Constants.BASE_URL, context, sharedManager, gson)
        .create(ApiInterface::class.java)

    private val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    fun newThread(action: () -> Unit) {
        Observable.fromCallable { action() }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            }, { e ->
                e.printStackTrace()
                parseError(e)
            })
    }

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

    private val sharedStoreList = arrayListOf<Store>()
    fun setSharedData(data: Store, fragmentName: String) {
        loge(fragmentName)
        val item = sharedStoreList.filter { it.id == data.id }
        if (item.isNotEmpty()) {
            for (i in 0 until sharedStoreList.size) {
                if (sharedStoreList[i].id == data.id) sharedStoreList[i] = data
            }
        } else sharedStoreList.add(data)
        sharedStore.value = ArrayList(sharedStoreList.filter { it.count != 0 })
    }

    fun clearSharedProducts() {
        sharedStoreList.clear()
        sharedStore.value = sharedStoreList
    }

    fun fetchData() {

        if (sharedManager.token.isNotEmpty()) {
            logi("Current token : " + sharedManager.token)

            getUser()
            getNews()
            getStreams()
            getUserAchievements()
            getUserNotifications()
            getStoreItems()
            getLessonsDay()
        }
    }

    fun login(phone: String, password: String) = compositeDisposable.add(
        api.login(LoginRequest(phone, password)).observeAndSubscribe()
            .subscribe({
                sharedManager.token = it.access_token
                data.value = it
                fetchData()
            }, {
                parseError(it)
            })
    )

    fun register(phone: String, code: String? = null) = compositeDisposable.add(
        api.register(RegisterRequest(phone, code)).observeAndSubscribe()
            .subscribe({
//                sharedManager.token = it.access_token
                data.value = it
            }, {
                parseError(it)
            })
    )

    fun setPassword(password: String) = compositeDisposable.add(
        api.setPassword(PasswordRequest(password)).observeAndSubscribe()
            .subscribe({
//                sharedManager.token = it.access_token
                data.value = it
            }, {
                parseError(it)
            })
    )

    private fun logOut() = compositeDisposable.add(
        api.logout().observeAndSubscribe()
            .subscribe({
                sharedManager.deleteAll()
            }, {
                parseError(it)
            })
    )

    fun getNews() = compositeDisposable.add(
        api.getNews().observeAndSubscribe()
            .subscribe({
                if (it.success) news.postValue(it.news_items)
            }, {
                parseError(it)
            })
    )

    fun getNewsDetail(newsId: Int) = compositeDisposable.add(
        api.getNewsDetail(newsId).observeAndSubscribe()
            .subscribe({
                if (it.success) data.postValue(it)
            }, {
                parseError(it)
            })
    )

    fun getComments(newsId: Int) = compositeDisposable.add(
        api.getComments(newsId).observeAndSubscribe()
            .subscribe({
                if (it.success) comments.postValue(ArrayList(it.news_item_comments))
            }, {
                parseError(it)
            })
    )

    fun addComment(newsId: Int, comment: String) = compositeDisposable.add(
        api.addComment(newsId, comment).observeAndSubscribe()
            .subscribe({
                if (it.success) {
                    data.postValue(it)
                    getComments(newsId)
                }
            }, {
                parseError(it)
            })
    )

    fun addLike(newsId: Int) = compositeDisposable.add(
        api.addLike(newsId).observeAndSubscribe()
            .subscribe({
                if (it.success) {
                    data.postValue(it)
                    getNews()
                    getNewsDetail(newsId)
                }
            }, {
                parseError(it)
            })
    )

    fun addBookmark(newsId: Int) = compositeDisposable.add(
        api.addBookmark(newsId).observeAndSubscribe()
            .subscribe({
                if (it.success) {
                    data.postValue(it)
                    getNews()
                    getNewsDetail(newsId)
                }
            }, {
                parseError(it)
            })
    )

    fun getChats() = compositeDisposable.add(
        api.getChats().observeAndSubscribe()
            .subscribe({
                if (it.success) chats.postValue(ArrayList(it.chat_items))
            }, {
                parseError(it)
            })
    )

    fun sendMessageChats(text: String) = compositeDisposable.add(
        api.sendMessageChats(text).observeAndSubscribe()
            .subscribe({
                if (it.success) getChats()
            }, {
                parseError(it)
            })
    )

    fun readMessageChats(chatId: Int) = compositeDisposable.add(
        api.readMessageChats(chatId).observeAndSubscribe()
            .subscribe({
                if (it.success) getChats()
            }, {
                parseError(it)
            })
    )

    fun getUser() = compositeDisposable.add(
        api.getUser().observeAndSubscribe()
            .subscribe({
                if (it.success) {
                    user.postValue(it.user)
                    sharedManager.user = it.user
                    sharedManager.userId = it.user.id
                }
            }, {
                parseError(it)
            })
    )

    fun getUserNotifications() = compositeDisposable.add(
        api.getUserNotifications().observeAndSubscribe()
            .subscribe({
                if (it.success) notifications.postValue(ArrayList(it.user_notifications))
            }, {
                parseError(it)
            })
    )

    fun getUserAchievements() = compositeDisposable.add(
        api.getUserAchievements().observeAndSubscribe()
            .subscribe({
                if (it.success) achievements.postValue(ArrayList(it.user_achievements))
            }, {
                parseError(it)
            })
    )

    fun getStreams() = compositeDisposable.add(
        api.getStreams().observeAndSubscribe()
            .subscribe({
                if (it.success) streams.postValue(ArrayList(it.streams))
            }, {
                parseError(it)
            })
    )

    fun getStreamMessages(streamId: Int) = compositeDisposable.add(
        api.getStreamMessages(streamId).observeAndSubscribe()
            .subscribe({
                if (it.success) streamsMessages.postValue(ArrayList(it.stream_chat_items))
            }, {
                parseError(it)
            })
    )

    fun sendStreamMessage(streamId: Int, message: String) = compositeDisposable.add(
        api.sendStreamMessages(streamId, message).observeAndSubscribe()
            .subscribe({
                if (it.success) getStreamMessages(streamId)
            }, {
                parseError(it)
            })
    )

    fun joinStream(streamId: Int) = compositeDisposable.add(
        api.joinStream(streamId).observeAndSubscribe()
            .subscribe({
            }, {
                parseError(it)
            })
    )

    fun getStoreItems() = compositeDisposable.add(
        api.getStoreItems().observeAndSubscribe()
            .subscribe({
                if (it.success) stores.postValue(ArrayList(it.store_items))
            }, {
                parseError(it)
            })
    )

    fun getStoreCategories() = compositeDisposable.add(
        api.getStoreCategories().observeAndSubscribe()
            .subscribe({
                if (it.success) data.postValue(it)
            }, {
                parseError(it)
            })
    )

    fun getLessonsDay() = compositeDisposable.add(
        api.getLessonsDay().observeAndSubscribe()
            .subscribe({
                if (it.success) lessonsDay.postValue(ArrayList(it.lesson_day_items))
            }, {
                parseError(it)
            })
    )

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

fun <T> Single<T>.observeAndSubscribe() =
    subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
