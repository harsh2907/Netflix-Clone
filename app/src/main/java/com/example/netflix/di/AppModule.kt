package com.example.netflix.di

import android.content.Context
import com.example.netflix.MainActivity
import com.example.netflix.data_source.remote.response.MovieResponse
import com.example.netflix.data_source.remote.response.RetrofitInterceptors
import com.example.netflix.data_source.utils.Constants
import com.example.netflix.domain.repository.MovieRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuth(): FirebaseAuth = Firebase.auth


    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient
    ):MovieResponse = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieResponse::class.java)

    @Provides
    @Singleton
    fun provideRepository(api:MovieResponse) = MovieRepository(api)

    @Provides
    @Singleton
    fun provideRetrofitClient(
       @ApplicationContext context: Context
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpDirectory = File(context.cacheDir, "responses")
//        val cacheSize = 10 * 1024 * 1024 //10MB
//        val cache = Cache(httpDirectory, cacheSize.toLong())
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(RetrofitInterceptors.RetryInterceptor(3))
            .addInterceptor(RetrofitInterceptors.CacheInterceptor(context))
            .callTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()


    }
}