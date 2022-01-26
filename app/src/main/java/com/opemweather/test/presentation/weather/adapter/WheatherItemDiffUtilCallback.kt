package com.opemweather.test.presentation.weather.adapter

import androidx.recyclerview.widget.DiffUtil
import com.opemweather.test.data.database.entity.PlaceWeatherShortEntity

class WheatherItemDiffUtilCallback : DiffUtil.ItemCallback<PlaceWeatherShortEntity>() {

    override fun areItemsTheSame(oldItem: PlaceWeatherShortEntity, newItem: PlaceWeatherShortEntity): Boolean =
        oldItem.placeCoordinatesEntity == newItem.placeCoordinatesEntity

    override fun areContentsTheSame(oldItem: PlaceWeatherShortEntity, newItem: PlaceWeatherShortEntity): Boolean =
        oldItem == newItem

}