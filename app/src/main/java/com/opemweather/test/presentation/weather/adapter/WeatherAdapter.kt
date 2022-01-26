package com.opemweather.test.presentation.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.opemweather.test.BuildConfig
import com.opemweather.test.data.database.entity.PlaceWeatherShortEntity
import com.opemweather.test.data.model.Status
import com.opemweather.test.databinding.ItemPlaceBinding
import com.opemweather.test.presentation.utils.DateTimeUtils
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class WeatherAdapter(
    private val listener: ItemActionsListener
) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    interface ItemActionsListener {
        fun onClickItem(item: PlaceWeatherShortEntity)
        fun onClickRefreshItem(item: PlaceWeatherShortEntity)
    }

    private val differ = AsyncListDiffer(this, WheatherItemDiffUtilCallback())

    fun getItem(position: Int): PlaceWeatherShortEntity = differ.currentList[position]

    fun swap(items: List<PlaceWeatherShortEntity>) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPlaceBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(
        private val binding: ItemPlaceBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PlaceWeatherShortEntity) {
            with(binding) {
                buttonRefresh.setOnClickListener { listener.onClickRefreshItem(item) }
                with(item) {
                    if (placeCoordinatesEntity.placeId != -1L) {
                        root.setOnClickListener { listener.onClickItem(item) }
                    } else {
                        root.setOnClickListener(null)
                    }
                    when (placeCoordinatesEntity.status) {
                        Status.LOADING -> progress.show()
                        Status.LOADED,
                        Status.FAIL -> progress.hide()
                    }
                    buttonRefresh.isEnabled = placeCoordinatesEntity.status != Status.LOADING
                    if (placeWeatherEntity != null && temperatureInfoEntity != null && coordinatesEntity != null) {
                        Glide.with(icon)
                            .load(
                                BuildConfig.IMAGE_URL.format(
                                    weatherInfoEntity?.firstOrNull()?.icon
                                )
                            )
                            .into(icon)
                        placeName.text = placeWeatherEntity.name
                        temperature.text = root.context.getString(
                            placeCoordinatesEntity.units.formatLabel,
                            temperatureInfoEntity.temperature
                        )
                        humidity.text = "${temperatureInfoEntity.humidity}%"
                        pressure.text = "${temperatureInfoEntity.pressure}"
                        val localeDateTime = DateTimeUtils.instantToLocalDateTime(placeWeatherEntity.updated, placeWeatherEntity.timeZone)
                        updatedAt.text = DateTimeUtils.formatShort(localeDateTime)
                        coordinates.text =
                            "${coordinatesEntity.latitude}, ${coordinatesEntity.longitude}"

                    } else {
                        coordinates.text =
                            "${placeCoordinatesEntity.latitude}, ${placeCoordinatesEntity.longitude}"
                    }
                }
            }
        }
    }
}