package com.opemweather.test.presentation.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

object DateTimeUtils {

    fun instantToLocalDateTime(instant: Instant, timeZone: Int): LocalDateTime =
        LocalDateTime.ofInstant(
            instant,
            ZoneOffset.ofTotalSeconds(timeZone)
        )

    fun formatShort(localDateTime: LocalDateTime): String =
        DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(localDateTime)

}