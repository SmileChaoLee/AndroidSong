package com.smile.androidsong.dagger.modules

import android.app.Activity
import com.smile.androidsong.model.Language
import com.smile.androidsong.model.Singer
import com.smile.androidsong.model.SingerType
import com.smile.androidsong.model.Song
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class PrimitiveDataModule {
    @Provides
    @Named("Activity")
    fun activityProvider(@Named("PrimitiveDataModule")activity : Activity?) : Activity? {
        return activity
    }

    @Provides
    @Named("LanguageArrayList")
    fun languageArrayListProvider(@Named("PrimitiveDataModule")list : ArrayList<Language>?) :
            ArrayList<Language>? {
        return list
    }

    @Provides
    @Named("SongArrayList")
    fun songArrayListProvider(@Named("PrimitiveDataModule")list : ArrayList<Song>?) :
            ArrayList<Song>? {
        return list
    }

    @Provides
    @Named("SingerTypeArrayList")
    fun singerTypeArrayListProvider(@Named("PrimitiveDataModule")list :
                                    ArrayList<SingerType>?) : ArrayList<SingerType>? {
        return list
    }

    @Provides
    @Named("SingerArrayList")
    fun singerArrayListProvider(@Named("PrimitiveDataModule")list :
                                    ArrayList<Singer>?) : ArrayList<Singer>? {
        return list
    }

    @Provides
    @Named("IntValue")
    fun intValueProvider(@Named("PrimitiveDataModule")intValue : Int?) : Int? {
        return intValue
    }

    @Provides
    @Named("FloatValue")
    fun floatValueProvider(@Named("PrimitiveDataModule")floatValue : Float?) : Float? {
        return floatValue
    }
}