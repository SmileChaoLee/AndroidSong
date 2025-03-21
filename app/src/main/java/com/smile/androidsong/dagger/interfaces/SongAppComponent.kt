package com.smile.androidsong.dagger.interfaces

import android.app.Activity
import com.smile.androidsong.LanguageListActivity
import com.smile.androidsong.SingerListActivity
import com.smile.androidsong.SingerTypeListActivity
import com.smile.androidsong.dagger.modules.PrimitiveDataModule
import com.smile.androidsong.retrofit_package.Client
import com.smile.androidsong.retrofit_package.RestApi
import com.smile.androidsong.dagger.modules.ListAdapterModule
import com.smile.androidsong.model.Language
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [Client::class,
    PrimitiveDataModule::class,
    ListAdapterModule::class])
interface SongAppComponent {
    fun inject(client: RestApi<Any>)
    fun inject(activity : SingerTypeListActivity)
    fun inject(activity : SingerListActivity)
    fun inject(activity : LanguageListActivity)
    @Component.Builder
    interface  Builder {
        fun build() : SongAppComponent
        @BindsInstance
        fun activityModule(@Named("PrimitiveDataModule") activity: Activity?) : Builder
        @BindsInstance
        fun arrayListModule(@Named("PrimitiveDataModule") list :
                                    ArrayList<Language>?) : Builder
        @BindsInstance
        fun intModule(@Named("PrimitiveDataModule") intValue : Int?) : Builder
        @BindsInstance
        fun floatModule(@Named("PrimitiveDataModule") floatValue : Float?) : Builder
    }
}