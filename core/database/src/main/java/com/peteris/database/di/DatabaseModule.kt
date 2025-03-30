package com.peteris.database.di

import android.content.Context
import androidx.room.Room
import com.peteris.database.AppDatabase
import org.koin.dsl.module

val databaseModule = module {

    single { providesAppDatabase(get()) }

    single { get<AppDatabase>().driverDao() }
}

fun providesAppDatabase(
    context: Context,
): AppDatabase = Room.databaseBuilder(
    context,
    AppDatabase::class.java,
    "app-database",
).build()