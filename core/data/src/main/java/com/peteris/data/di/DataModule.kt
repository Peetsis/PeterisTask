package com.peteris.data.di

import coil3.ImageLoader
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.peteris.data.helper.ConnectionMonitor
import com.peteris.data.helper.ConnectionMonitorImpl
import com.peteris.data.repository.SportsRepository
import com.peteris.data.repository.SportsRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single<SportsRepository> { SportsRepositoryImpl(get(), get()) }
    single<ConnectionMonitor> { ConnectionMonitorImpl(get()) }
    single {
        ImageLoader.Builder(androidContext())
            .crossfade(true)
            .logger(DebugLogger())
            .build()
    }

}