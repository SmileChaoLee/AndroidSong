package com.smile.androidsong.dagger.interfaces

import android.app.Activity
import com.smile.androidsong.LanguageListActivity
import com.smile.androidsong.SingerListActivity
import com.smile.androidsong.SingerTypeListActivity
import com.smile.androidsong.SongListActivity
import com.smile.androidsong.WordListActivity
import com.smile.androidsong.dagger.modules.PrimitiveDataModule
import com.smile.smilelibraries.retrofit.Client
import com.smile.androidsong.retrofit_package.RestApi
import com.smile.androidsong.dagger.modules.ListAdapterModule
import com.smile.androidsong.model.Language
import com.smile.androidsong.model.Singer
import com.smile.androidsong.model.SingerType
import com.smile.androidsong.model.Song
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
    fun inject(activity : SongListActivity)
    fun inject(activity : WordListActivity)
    @Component.Builder
    interface  Builder {
        fun build() : SongAppComponent
        @BindsInstance
        fun activityModule(@Named("PrimitiveDataModule") activity: Activity?) : Builder
        @BindsInstance
        fun languageArrayListModule(@Named("PrimitiveDataModule") list :
                                    ArrayList<Language>?) : Builder
        @BindsInstance
        fun songArrayListModule(@Named("PrimitiveDataModule") list :
                            ArrayList<Song>?) : Builder
        @BindsInstance
        fun singerTypeArrayListModule(@Named("PrimitiveDataModule") list :
                                ArrayList<SingerType>?) : Builder
        @BindsInstance
        fun singerArrayListModule(@Named("PrimitiveDataModule") list :
                                      ArrayList<Singer>?) : Builder
        @BindsInstance
        fun intModule(@Named("PrimitiveDataModule") intValue : Int?) : Builder
        @BindsInstance
        fun floatModule(@Named("PrimitiveDataModule") floatValue : Float?) : Builder
    }
}