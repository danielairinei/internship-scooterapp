package com.internship.move

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.internship.move.presentation.map.viewmodel.MapViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MapViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}