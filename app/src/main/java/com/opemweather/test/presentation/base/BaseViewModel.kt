package com.opemweather.test.presentation.base

import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router

abstract class BaseViewModel(
    protected val router: Router
): ViewModel() {

    open fun onBackPressed() {
        router.exit()
    }
}