package com.opemweather.test.presentation.settings

import com.github.terrakok.cicerone.Router
import com.opemweather.test.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    router: Router
): BaseViewModel(router) {
}