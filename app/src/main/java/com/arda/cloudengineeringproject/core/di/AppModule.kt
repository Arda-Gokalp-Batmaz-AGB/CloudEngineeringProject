package com.arda.cloudengineeringproject.core.di

import android.content.Context
import coil.ImageLoader
import coil.imageLoader
import com.arda.cloudengineeringproject.App
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun ImageLoader(@ApplicationContext context: Context): ImageLoader = App.context.imageLoader

}

//@Retention(AnnotationRetention.BINARY)
//@Qualifier
//annotation class IoDispatcher