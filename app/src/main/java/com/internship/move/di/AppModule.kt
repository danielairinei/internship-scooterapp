package com.internship.move.di

import com.internship.move.data.dto.ErrorResponse
import com.internship.move.networking.interceptor.AuthenticationTokenProvider
import com.internship.move.networking.interceptor.RuntimeAuthenticationTokenProvider
import com.internship.move.networking.interceptor.SessionInterceptor
import com.internship.move.networking.service.RideApi
import com.internship.move.networking.service.ScooterApi
import com.internship.move.networking.service.UserApi
import com.internship.move.presentation.authentification.viewmodel.AuthenticationViewModel
import com.internship.move.presentation.map.viewmodel.MapViewModel
import com.internship.move.presentation.menu.viewmodel.MenuViewModel
import com.internship.move.presentation.splash.viewmodel.SplashViewModel
import com.internship.move.repository.RideRepository
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
    single { provideSessionInterceptor(get()) }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get(), get()) }
    single { UserRepository(get(), provideUserApi(get())) }
    factory { provideErrorResponseJsonAdapter(get()) }
}

val scooterRepository = module {
    single { provideMoshi() }
    single { provideSessionInterceptor(get()) }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get(), get()) }
    single { ScooterRepository(get(), provideScooterApi(get())) }
    factory { provideErrorResponseJsonAdapter(get()) }
}

val rideRepository = module {
    single { provideMoshi() }
    single { provideSessionInterceptor(get()) }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get(), get()) }
    single { RideRepository(get(), provideRideApi(get())) }
    factory { provideErrorResponseJsonAdapter(get()) }
}

val accessors = module {
    factory<AuthenticationTokenProvider> { RuntimeAuthenticationTokenProvider(get()) }
}

val viewModels = module {
    viewModel { AuthenticationViewModel(get(), get()) }
    viewModel { MapViewModel(get(), get(), get(), get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { MenuViewModel(get(), get()) }
}

private fun provideSessionInterceptor(authenticationTokenProvider: AuthenticationTokenProvider): SessionInterceptor =
    SessionInterceptor(authenticationTokenProvider)

private fun provideErrorResponseJsonAdapter(moshi: Moshi): JsonAdapter<ErrorResponse> =
    moshi.adapter(ErrorResponse::class.java).lenient()

private fun provideMoshi(): Moshi = Moshi.Builder().build()

private fun provideOkHttpClient(sessionInterceptor: SessionInterceptor) =
    OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .addInterceptor(sessionInterceptor)
        .build()

private fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(okHttpClient)
    .build()

private fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

private fun provideScooterApi(retrofit: Retrofit): ScooterApi = retrofit.create(ScooterApi::class.java)

private fun provideRideApi(retrofit: Retrofit): RideApi = retrofit.create(RideApi::class.java)
