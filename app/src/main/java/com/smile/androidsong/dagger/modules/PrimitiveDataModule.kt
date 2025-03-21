package com.smile.androidsong.dagger.modules

import android.app.Activity
import com.smile.androidsong.model.Language
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
    @Named("LanguageList")
    fun languageListProvider(@Named("PrimitiveDataModule")list : ArrayList<Language>?) :
            ArrayList<Language>? {
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