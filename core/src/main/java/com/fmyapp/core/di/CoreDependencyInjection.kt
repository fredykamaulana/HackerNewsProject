package com.fmyapp.core.di

import com.fmyapp.core.BuildConfig
import com.fmyapp.core.data.service.ItemService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

fun injectCoreKoinModules() = loadModules

private val loadModules by lazy {
    loadKoinModules(modules)
}

@Suppress("SpellCheckingInspection")
val networkModule: Module = module {

    single {
        if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build()
        } else {
            OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build()
        }
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create().withNullSerialization())
            .build()
    }
}

val apiModule: Module = module {
    single { get<Retrofit>().create(ItemService::class.java) }
}

val modules = listOf(
    networkModule,
    apiModule
)