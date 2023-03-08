package ru.cft.shift2023winter.di

import android.content.res.Resources
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.cft.shift2023winter.presentation.LocationViewModel
import ru.cft.shift2023winter.presentation.WeatherViewModel
import ru.cft.shift2023winter.presentation.adapter.WeatherAdapter

private fun provideWeatherAdapter(resources: Resources) = WeatherAdapter(resources)

fun providePresentationModule(): Module = module {
    single {
        provideWeatherAdapter(androidContext().resources)
    }
    viewModel {
        WeatherViewModel(
            get(), get(), get(), get(), get(), get()
        )
    }
    viewModel {
        LocationViewModel(
            get(), get(), get()
        )
    }
}