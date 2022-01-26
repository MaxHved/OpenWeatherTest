package com.opemweather.test.presentation.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.opemweather.test.R

class SettingsFragment: PreferenceFragmentCompat() {

    companion object {
        fun newInstance(): SettingsFragment = SettingsFragment()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)
    }
}