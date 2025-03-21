package com.smile.androidsong.dagger.modules

import dagger.Module
import dagger.Provides

@Module
class JavaPrimitiveDataModule {
    @Provides
    fun <T> arrayListProvider(value : T) : T {
        return value
    }
}