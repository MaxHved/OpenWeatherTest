package com.opemweather.test.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.opemweather.test.R
import com.opemweather.test.databinding.FragmentSettingsWrapperBinding
import com.opemweather.test.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingWrapperFragment: BaseFragment<FragmentSettingsWrapperBinding, SettingsViewModel>() {

    companion object {
        fun newInstance(): SettingWrapperFragment = SettingWrapperFragment()
    }

    override val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding  = FragmentSettingsWrapperBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.setNavigationOnClickListener { viewModel.onBackPressed() }
        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.inner_frame_container, SettingsFragment.newInstance())
                .commit()
        }
    }

}