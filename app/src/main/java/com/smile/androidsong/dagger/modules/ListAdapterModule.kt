package com.smile.androidsong.dagger.modules

import android.app.Activity
import com.smile.androidsong.model.Language
import com.smile.androidsong.view_adapter.LanguageListAdapter
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ListAdapterModule {
    @Provides
    fun languageListAdapterProvider(@Named("Activity")activity : Activity?,
                                    @Named("LanguageArrayList")languages : ArrayList<Language>?,
                                    @Named("IntValue")orderedFrom : Int?,
                                    @Named("FloatValue")textFontSize : Float?
    ) : LanguageListAdapter {
        return LanguageListAdapter(activity!!, languages!!, orderedFrom!!, textFontSize!!)
    }
    /*
    fun languageListAdapterProvider() : LanguageListAdapter {
        return LanguageListAdapter(Activity(), ArrayList<Language>(),
            0, 100f)
    }
    */
}