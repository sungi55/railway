package com.sunhurov.repository.di

import com.sunhurov.repository.*
import com.sunhurov.repository.station.StationRepository
import com.sunhurov.repository.station.StationRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val repositoryModule = module {
    factory { AppDispatchers(Dispatchers.Main, Dispatchers.IO) }
    factory<StationRepository> { StationRepositoryImpl(get(), get(), get(),get()) }
}