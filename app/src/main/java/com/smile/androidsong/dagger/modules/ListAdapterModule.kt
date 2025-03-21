package com.smile.androidsong.view_adapter

import android.app.Activity
import com.smile.androidsong.model.Language
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ListAdapterModule {
    @Provides
    @Named("LanguageListAdapter")
    fun LanguageListAdapterProvider(activity : Activity,
                                    languages : ArrayList<Language>,
                                    orderedFrom : Int,
                                    textFontSize : Float
    ) : LanguageListAdapter {
        return LanguageListAdapter(activity, languages, orderedFrom, textFontSize)
    }
}