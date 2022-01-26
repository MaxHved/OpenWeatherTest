package com.opemweather.test.presentation.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.opemweather.test.R
import com.opemweather.test.data.database.entity.PlaceWeatherShortEntity
import com.opemweather.test.databinding.FragmentWeatherBinding
import com.opemweather.test.presentation.base.BaseFragment
import com.opemweather.test.presentation.weather.adapter.WeatherAdapter
import com.opemweather.test.presentation.weather.details.WeatherDetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class WeatherFragment: BaseFragment<FragmentWeatherBinding, WeatherViewModel>() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    override val viewModel: WeatherViewModel by viewModels()

    private val itemActionsListener = object: WeatherAdapter.ItemActionsListener {
        override fun onClickItem(item: PlaceWeatherShortEntity) {
            parentFragmentManager.beginTransaction()
                .add(WeatherDetailsFragment.newInstance(item.placeCoordinatesEntity.placeId), "")
                .commit()
        }

        override fun onClickRefreshItem(item: PlaceWeatherShortEntity) {
            viewModel.refreshPlaceWeather(item)
        }

    }

    private val adapter = WeatherAdapter(itemActionsListener)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            toolbar.setOnMenuItemClickListener(toolbarMenuItemClickListener)
            fabAddPlace.setOnClickListener { viewModel.pickPlace() }

            places.adapter = adapter
            subscribe {
                 viewModel.placeWeathers.collect { items ->
                     places.isVisible = items.isNotEmpty()
                     adapter.swap(items)
                     labelEmptyData.isVisible = items.isEmpty()
                 }
            }
        }
    }

    private val toolbarMenuItemClickListener = Toolbar.OnMenuItemClickListener { menuItem ->
        when(menuItem.itemId) {
            R.id.item_settings -> {
                viewModel.forwardSettings()
                true
            }
            else -> false

        }
    }
}