package ru.practicum.android.diploma.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.migration.Migration
import com.google.gson.Gson
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.common.data.db.AppDatabase
import ru.practicum.android.diploma.favorites.data.local.DbClient
import ru.practicum.android.diploma.favorites.data.local.LocalClient
import ru.practicum.android.diploma.search.data.network.HhApiService
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.search.mapper.ShortVacancyResponseMapper
import ru.practicum.android.diploma.vacancy.mapper.StringListConverter

private const val API_BASE_URL = "https://api.hh.ru/"
private const val SHARED_PREFERENCES_NAME = "shared_preferences"

val dataModule = module {

    single<HhApiService> {
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HhApiService::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(hhApiService = get())
    }

    single<AppDatabase> {
        Room.databaseBuilder(get(), AppDatabase::class.java, "database.db")
            .addTypeConverter(StringListConverter(get()))
            .fallbackToDestructiveMigration()
            .build()
    }

    single<SharedPreferences> {
        androidContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    factory { Gson() }

    single {
        androidApplication()
    }

    single<LocalClient> {
        DbClient()
    }

    single { ShortVacancyResponseMapper }
}
