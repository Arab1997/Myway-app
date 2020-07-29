package com.snatap.myway.di

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.snatap.myway.base.BaseViewModel
import com.snatap.myway.network.ErrorResp
import com.snatap.myway.network.models.Comment
import com.snatap.myway.network.models.News
import com.snatap.myway.utils.preferences.PreferenceHelper
import com.snatap.myway.utils.preferences.SharedManager
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


val viewModelModule = module {

    fun provideMutableLiveData() = MutableLiveData<Any>()

    viewModel { BaseViewModel(get(), get(), get()) }

    single { provideMutableLiveData() }
    single(named("sharedLive")) { provideMutableLiveData() }
    single(named("errorLive")) { MutableLiveData<ErrorResp>() }
    single(named("news")) { MutableLiveData<ArrayList<News>>() }
    single(named("comments")) { MutableLiveData<ArrayList<Comment>>() }
}

val networkModule = module {

    fun provideGson() = Gson()

    single { provideGson() }
}

val sharedPrefModule = module {

    factory { PreferenceHelper.customPrefs(get(), "Qweep") }

    factory { SharedManager(get(), get(), get()) }
}
