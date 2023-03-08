package ru.cft.shift2023winter.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.cft.shift2023winter.data.LocalDateTimeDeserializer
import ru.cft.shift2023winter.data.api.ApiKey
import ru.cft.shift2023winter.data.api.GeoCodingApi
import ru.cft.shift2023winter.data.api.WeatherApi
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://api.openweathermap.org/"
const val API_KEY = "INSERT_YOUR_API_KEY_HERE"
const val CALL_TIMEOUT = 10L
const val READ_TIMEOUT = 10L
const val CONNECT_TIMEOUT = 10L

private fun provideDeserializerGson(): Gson = GsonBuilder()
    .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer)
    .serializeSpecialFloatingPointValues()
    .create()

private fun provideOkHttpClient(): OkHttpClient =
    OkHttpClient.Builder()
        .callTimeout(CALL_TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .build()

private fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson, baseUrl: String): Retrofit =
    Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(baseUrl)
        .build()

private fun provideWeatherApi(okHttpClient: OkHttpClient, gson: Gson): WeatherApi =
    provideRetrofit(okHttpClient, gson, BASE_URL).create(WeatherApi::class.java)

private fun provideGeoCodingApi(okHttpClient: OkHttpClient, gson: Gson): GeoCodingApi =
    provideRetrofit(okHttpClient, gson, BASE_URL).create(GeoCodingApi::class.java)

private fun provideApiKey(): ApiKey = ApiKey(API_KEY)


fun provideNetworkModule(): Module =
    module {
        factory {
            provideOkHttpClient()
        }
        factory(named("deserializerGson")) {
            provideDeserializerGson()
        }
        single {
            provideApiKey()
        }
        single {
            provideWeatherApi(get(), get(named("deserializerGson")))
        }
        single {
            provideGeoCodingApi(get(), get(named("deserializerGson")))
        }
    }