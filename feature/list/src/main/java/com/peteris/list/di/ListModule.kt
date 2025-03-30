package com.peteris.list.di

import com.peteris.list.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val listModule = module {
    viewModel { ListViewModel(get(), get()) }
}