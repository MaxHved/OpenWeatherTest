package com.opemweather.test.presentation.weather.details

import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.opemweather.test.databinding.FragmentWeatherDetailsBinding
import com.opemweather.test.presentation.utils.DateTimeUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class WeatherDetailsFragment : DialogFragment() {

    companion object {
        private const val ARG_PLACE_ID = "arg_place_id"

        fun newInstance(placeId: Long) = WeatherDetailsFragment().apply {
            arguments = Bundle().apply {
                putLong(ARG_PLACE_ID, placeId)
            }
        }
    }

    private val viewModel: WeatherDetailsViewModel by viewModels()
    private lateinit var binding: FragmentWeatherDetailsBinding
    private var job: Job? = null
    private val placeId: Long
        get() = requireArguments().getLong(ARG_PLACE_ID)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(placeId)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentWeatherDetailsBinding.inflate(requireActivity().layoutInflater)
        job = lifecycleScope.launch {
            viewModel.placeWeather.collect { placeWeather ->
                with(binding) {
                    with(placeWeather) {
                        toolbar.title = placeWeatherEntity?.name
                        val unitsFormatLabel = placeCoordinatesEntity.units.formatLabel
                        valueTemperatureMin.text =
                            getString(unitsFormatLabel, temperatureInfoEntity?.temperatureMin ?: 0)
                        valueTemperatureMax.text =
                            getString(unitsFormatLabel, temperatureInfoEntity?.temperatureMax ?: 0)
                        valueTemperatureFeelsLike.text =
                            getString(unitsFormatLabel, temperatureInfoEntity?.feelsLike ?: 0)
                        valueWindSpeed.text = "${windEntity?.speed ?: ""}"
                        sysEntity?.run {
                            placeWeatherEntity?.run {
                                bindLocalDateTime(valueSunrise, sunrise, timeZone)
                                bindLocalDateTime(valueSunset, sunset, timeZone)
                            }

                        }
                    }
                }
            }
        }
        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setPositiveButton(android.R.string.ok, null)
            .create()
    }


    private fun bindLocalDateTime(textView: TextView, instant: Instant, timeZone: Int) {
        val localeDateTime = DateTimeUtils.instantToLocalDateTime(instant, timeZone)
        textView.text = DateTimeUtils.formatShort(localeDateTime)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job?.cancel()
    }

}