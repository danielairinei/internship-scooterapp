package com.internship.move

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _appContext = this.application
    }

    companion object {
        private var _appContext: Application? = null
        val appContext: Application
            get() = _appContext ?: throw IllegalArgumentException("App context not initialized")
    }
}