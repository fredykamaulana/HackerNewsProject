package com.fmyapp.codebase

import android.os.Bundle
import androidx.navigation.Navigation
import com.fmyapp.codebase.databinding.ActivityMainBinding
import com.fmyapp.core.presentation.abstraction.BaseActivity
import com.fmyapp.core.presentation.delegates.viewBinding

class MainActivity : BaseActivity() {
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    private val navController by lazy {
        Navigation.findNavController(this, R.id.navHostFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}