package ru.practicum.android.diploma.di

import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.search.data.network.HhApiService
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.RetrofitNetworkClient

private const val API_BASE_URL = "https://api.hh.ru/"

val dataModule = module {

    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("User-Agent", "HH Quickly Searcher/1.0 (pisanyy95@gmail.com)")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    single<HhApiService> {
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HhApiService::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(hhApiService = get())
    }

    factory { Gson() }

    single {
        androidApplication()
    }
}
