package com.smile.androidsong.interfaces

import com.smile.androidsong.LanguageListActivity
import com.smile.androidsong.SingerListActivity
import com.smile.androidsong.SingerTypeListActivity
import com.smile.androidsong.retrofit_package.Client
import com.smile.androidsong.retrofit_package.RestApi
import com.smile.androidsong.view_adapter.ListAdapterModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [Client::class, ListAdapterModule::class])
interface SongAppComponent {
    fun inject (client: RestApi<Any>)
    fun inject (activity : SingerTypeListActivity)
    fun inject (activity : SingerListActivity)
    fun inject (activity : LanguageListActivity)

    @Component.Builder
    interface  Builder {
        fun build() : ListAdapterModule
    }
}