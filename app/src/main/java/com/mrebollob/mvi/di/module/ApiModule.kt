package com.mrebollob.mvi.di.module


import android.app.Application
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.mrebollob.mvi.BuildConfig
import com.mrebollob.mvi.data.PostRepositoryImp
import com.mrebollob.mvi.data.api.PostApiService
import com.mrebollob.mvi.di.annotation.BaseUrl
import com.mrebollob.mvi.domain.repository.PostRepository
import com.mrebollob.mvi.platform.schedulers.BaseSchedulerProvider
import com.mrebollob.mvi.presentation.posts.PostsProcessorHolder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun providePostsProcessorHolder(
        postRepository: PostRepository,
        schedulerProvider: BaseSchedulerProvider
    ): PostsProcessorHolder {

        return PostsProcessorHolder(postRepository, schedulerProvider)
    }

    @Provides
    @Singleton
    fun provideRepository(apiService: PostApiService): PostRepository = PostRepositoryImp(apiService)

    @Provides
    @Singleton
    fun provideApiService(
        okHttpClient: OkHttpClient,
        @BaseUrl baseUrl: String
    ): PostApiService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(PostApiService::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        context: Application
    ): OkHttpClient {

        val cacheSize = (5 * 1024 * 1024).toLong()
        val cache = Cache(context.cacheDir, cacheSize)

        return OkHttpClient.Builder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            cache(cache)
            if (BuildConfig.DEBUG) addInterceptor(httpLoggingInterceptor)
        }.build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = BuildConfig.BASE_URL
}