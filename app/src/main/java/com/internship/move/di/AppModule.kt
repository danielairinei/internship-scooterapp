package com.internship.move.di

import com.internship.move.data.dto.UserApi
import com.internship.move.presentation.authentification.viewmodel.AuthenticationViewModel
import com.internship.move.presentation.map.viewmodel.MapViewModel
import com.internship.move.presentation.splash.viewmodel.SplashViewModel
import com.internship.move.repository.UserRepository
import com.internship.move.utils.InternalStorageManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://scooter-app.herokuapp.com/"

val internalStorage = module {
    single { InternalStorageManager(androidContext()) }
}

val repositories = module {
    single { UserRepository(get(), provideUserApi()) }
}

val viewModels = module {
    viewModel { AuthenticationViewModel(get()) }
    viewModel { MapViewModel(get()) }
    viewModel { SplashViewModel(get()) }
}

fun provideUserApi(): UserApi {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(UserApi::class.java)
}

val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()