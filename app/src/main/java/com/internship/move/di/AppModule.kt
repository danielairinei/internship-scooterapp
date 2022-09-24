package com.internship.move.di

import com.internship.move.data.dto.ErrorResponse
import com.internship.move.data.dto.scooter.ScooterApi
import com.internship.move.data.dto.user.UserApi
import com.internship.move.presentation.authentification.viewmodel.AuthenticationViewModel
import com.internship.move.presentation.map.viewmodel.MapViewModel
import com.internship.move.presentation.menu.viewmodel.MenuViewModel
import com.internship.move.presentation.splash.viewmodel.SplashViewModel
import com.internship.move.repository.ScooterRepository
import com.internship.move.repository.UserRepository
import com.internship.move.utils.InternalStorageManager
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://scooter-app.herokuapp.com"

val internalStorage = module {
    single { InternalStorageManager(androidContext()) }
}

val userRepository = module {
    single { provideMoshi() }
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), get()) }
    single { UserRepository(get(), provideUserApi(get())) }
    factory { provideErrorResponseJsonAdapter(get()) }

}

val scooterRepository = module{
    single { provideMoshi() }
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), get()) }
    single { ScooterRepository(get(), provideScooterApi(get())) }
    factory { provideErrorResponseJsonAdapter(get()) }
}

val viewModels = module {
    viewModel { AuthenticationViewModel(get(),get()) }
    viewModel { MapViewModel(get(),get(),get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { MenuViewModel(get()) }
}

fun provideErrorResponseJsonAdapter(moshi: Moshi): JsonAdapter<ErrorResponse> =
    moshi.adapter(ErrorResponse::class.java).lenient()

fun provideMoshi(): Moshi = Moshi.Builder().build()

fun provideOkHttpClient() =
    OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }).build()

fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(okHttpClient)
    .build()

fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

fun provideScooterApi(retrofit: Retrofit): ScooterApi = retrofit.create(ScooterApi::class.java)