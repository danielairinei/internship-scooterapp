package com.internship.move

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.internship.move.di.accessors
import com.internship.move.di.internalStorage
import com.internship.move.di.scooterRepository
import com.internship.move.di.userRepository
import com.internship.move.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@MainActivity)
            modules(
                listOf(
                    internalStorage,
                    viewModels,
                    userRepository,
                    scooterRepository,
                    accessors
                )
            )
        }
    }
}