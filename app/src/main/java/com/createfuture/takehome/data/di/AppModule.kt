package com.createfuture.takehome.data.di

import android.content.Context
import com.createfuture.takehome.data.remote.CharactersApi
import com.createfuture.takehome.data.remote.HeaderInterceptor
import com.createfuture.takehome.data.remote.NetworkStatusInterceptor
import com.createfuture.takehome.data.repository.CharactersRepositoryImpl
import com.createfuture.takehome.data.util.TokenManager
import com.createfuture.takehome.domain.repository.CharactersRepository
import com.createfuture.takehome.domain.usecase.GetCharactersUseCase
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://yj8ke8qonl.execute-api.eu-west-1.amazonaws.com"

    @Singleton
    @Provides
    fun provideCheckNetworkInterceptor(@ApplicationContext context: Context) =
        NetworkStatusInterceptor(context)

    @Singleton
    @Provides
    fun provideOkHttpClient(
        tokenInterceptor: HeaderInterceptor,
        checkNetworkInterceptor: NetworkStatusInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(checkNetworkInterceptor)
        .addInterceptor(tokenInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Singleton
    @Provides
    fun provideCharacterApi(retrofit: Retrofit): CharactersApi =
        retrofit.create(CharactersApi::class.java)

    @Singleton
    @Provides
    fun provideAuthTokenProvider(
        @ApplicationContext appContext: Context
    ) = TokenManager(appContext)

    @Singleton
    @Provides
    fun provideCharactersRepository(
        api: CharactersApi,
    ): CharactersRepository = CharactersRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideGetCharactersUseCase(repository: CharactersRepository) = GetCharactersUseCase(repository)
}