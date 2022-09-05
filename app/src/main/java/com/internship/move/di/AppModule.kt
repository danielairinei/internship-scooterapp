package com.internship.move.di

import com.internship.move.presentation.authentification.viewmodel.AuthenticationViewModel
import com.internship.move.presentation.map.viewmodel.MapViewModel
import com.internship.move.presentation.splash.viewmodel.SplashViewModel
import com.internship.move.repository.Repository
import com.internship.move.utils.InternalStorageManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val internalStorage = module {
    single { InternalStorageManager(androidContext()) }
}

val repositories = module {
    single { Repository(get()) }
}

val viewModels = module {
    viewModel { AuthenticationViewModel(get()) }
    viewModel { MapViewModel(get()) }
    viewModel { SplashViewModel(get()) }
}