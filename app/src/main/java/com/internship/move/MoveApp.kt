package com.internship.move

import android.app.Application
import com.internship.move.di.internalStorage
import com.internship.move.di.scooterRepository
import com.internship.move.di.userRepository
import com.internship.move.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class MoveApp: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        GlobalContext.startKoin {
            androidContext(this@MoveApp)
            modules(
                listOf(
                    internalStorage,
                    viewModels,
                    userRepository,
                    scooterRepository
                )
            )
        }
    }
}