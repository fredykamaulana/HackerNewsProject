@file:Suppress("unused")

package com.fmyapp.core.presentation.abstraction

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.fmyapp.core.di.injectCoreKoinModules

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        injectCoreKoinModules()
    }

}