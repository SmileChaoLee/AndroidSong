package com.smile.retrofit_package

import android.util.Log
import com.smile.model.Language
import com.smile.model.LanguageList
import com.smile.model.SingerTypeList
import com.smile.model.SongList
import retrofit2.Callback

@JvmDefaultWithCompatibility
interface RestApiKotlin<T> : Callback<T> {

    companion object {
        const val TAG = "RestApiKotlin"
    }

    private val callback : Callback<T>
        get() {
            return this
        }
    // get Retrofit client and Retrofit Api
    private val apiInterface : ApiInterface
        get() {
            return Client.getInstance().create(ApiInterface::class.java)
        }

    fun getAllSingerTypes() {
        Log.d(TAG, "getAllSingerTypes")
        // get Call from Retrofit Api
        apiInterface.allSingerTypes.apply {
            enqueue(callback as Callback<SingerTypeList>)
        }
    }

    fun getAllLanguages() {
        Log.d(TAG, "getAllLanguages")
        // get Call from Retrofit Api
        apiInterface.allLanguages.apply {
            enqueue(callback as Callback<LanguageList>)
        }
    }

    fun getSongsByLanguageIdOrderBy(language : Language, pageSize : Int,
                                    pageNo : Int) {
        Log.d(TAG, "getSongsByLanguageIdOrderBy")
        val languageId = language.id
        // order by (number of words + song's name)
        val orderBy = "NumWordsSongNa"
        // get Call from Retrofit Api
        apiInterface.getSongsByLanguageIdOrderBy(languageId,
            pageSize, pageNo, orderBy).apply {
            enqueue(callback as Callback<SongList>)
        }
    }

    fun getSongsByLanguageIdOrderByWithFilter(language : Language,
                                              pageSize : Int,
                                              pageNo : Int,
                                              // filter cannot be empty
                                              filter: String) {
        Log.d(TAG, "getSongsByLanguageIdOrderByWithFilter")
        val languageId = language.id
        // order by (number of words + song's name)
        val orderBy = "NumWordsSongNa"
        // get Call from Retrofit Api
        apiInterface.getSongsByLanguageIdOrderByWithFilter(
            languageId,
            pageSize,
            pageNo,
            orderBy,
            filter  // cannot be empty
        ).apply {
            enqueue(callback as Callback<SongList>)
        }
    }
}