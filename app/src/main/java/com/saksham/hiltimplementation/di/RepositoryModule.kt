package com.saksham.hiltimplementation.di

import com.saksham.hiltimplementation.network.BlogRetrofit
import com.saksham.hiltimplementation.network.NetworkMapper
import com.saksham.hiltimplementation.repository.MainRepository
import com.saksham.hiltimplementation.room.BlogDao
import com.saksham.hiltimplementation.room.CacheMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        blogDao: BlogDao,
        retrofit: BlogRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): MainRepository{
        return MainRepository(blogDao,retrofit,cacheMapper,networkMapper)
    }
}