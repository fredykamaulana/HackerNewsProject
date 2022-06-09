package com.fmyapp.core.presentation.abstraction

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.fmyapp.core.di.injectCoreKoinModules
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment(layout: Int) : Fragment(layout) {

    override fun onAttach(context: Context) {
        injectCoreKoinModules()
        super.onAttach(context)
    }

    fun showSnackBar(
        view: View,
        message: String,
        action: String = "RETRY",
        actionClick: () -> Unit = {}
    ) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            .setAction(action) { actionClick() }
            .show()
    }
}