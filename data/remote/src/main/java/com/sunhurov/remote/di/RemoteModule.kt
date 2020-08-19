package com.sunhurov.remote.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sun.javaws.Globals
import com.sunhurov.remote.RailwayDatasource
import com.sunhurov.remote.RailwayService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun createRemoteModule(baseUrl: String) = module {

    factory<Interceptor> {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    factory { OkHttpClient.Builder()
        .addInterceptor(get())
        .addInterceptor{chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("X-KOLEO-Version", "1")
                .build()
            chain.proceed(newRequest)
        }
        .build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    factory{ get<Retrofit>().create(RailwayService::class.java) }

    factory { RailwayDatasource(get()) }
}