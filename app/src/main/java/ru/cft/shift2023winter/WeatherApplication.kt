package ru.cft.shift2023winter

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.cft.shift2023winter.di.provideDataModule
import ru.cft.shift2023winter.di.provideDomainModule
import ru.cft.shift2023winter.di.provideNetworkModule

class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@WeatherApplication)
            modules(provideNetworkModule(), provideDataModule(), provideDomainModule())
        }
    }
}