package com.sunhurov.details.di

import com.sunhurov.details.DetailViewModel
import com.sunhurov.details.domain.GetDistanceDetailsUseCase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureDetailModule = module {
    factory { GetDistanceDetailsUseCase(get()) }
    viewModel { DetailViewModel(get(), get()) }
}