package com.sunhurov.home.di

import com.sunhurov.home.HomeViewModel
import com.sunhurov.home.domain.GetStationsUseCase
import com.sunhurov.home.domain.SearchStationsUseCase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureHomeModule = module {
    factory { SearchStationsUseCase(get()) }
    factory { GetStationsUseCase(get()) }
    viewModel { HomeViewModel(get(), get(), get()) }
}