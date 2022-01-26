package com.opemweather.test.presentation

import com.github.terrakok.cicerone.Router
import com.opemweather.test.presentation.base.BaseViewModel
import com.opemweather.test.presentation.screens.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
     router: Router
): BaseViewModel(router) {

    init {
        router.newRootScreen(Screens.weather())
    }
}