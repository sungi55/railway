package com.sunhurov.railway.di

import com.sunhurov.common.BuildConfig
import com.sunhurov.details.di.featureDetailModule
import com.sunhurov.home.di.featureHomeModule
import com.sunhurov.local.di.localModule
import com.sunhurov.pref.di.prefModule
import com.sunhurov.remote.di.createRemoteModule
import com.sunhurov.repository.di.repositoryModule

val appComponent = listOf(
    createRemoteModule(BuildConfig.BASE_URL),
    repositoryModule,
    localModule,
    prefModule,
    featureHomeModule,
    featureDetailModule
)