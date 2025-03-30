package com.peteris.peteristask.di

import com.peteris.data.di.dataModule
import com.peteris.database.di.databaseModule
import com.peteris.designsystem.di.designSystemModule
import com.peteris.detail.di.detailModule
import com.peteris.list.di.listModule
import com.peteris.network.di.networkModule
import com.peteris.peteristask.BuildConfig
import org.koin.core.module.Module

fun moduleList(): List<Module> = listOf(
    databaseModule,
    detailModule,
    dataModule,
    designSystemModule,
    listModule,
    networkModule(BuildConfig.SPORTS_KEY)
)