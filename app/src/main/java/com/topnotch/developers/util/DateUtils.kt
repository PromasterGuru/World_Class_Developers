package com.topnotch.developers.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by promasterguru on 17/10/2021.
 */
class DateUtils {
    companion object {
        fun simpleMonthDayYearDate(dt: String): String {
            return try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
                val outputFormat = SimpleDateFormat("MMM, d yyyy", Locale.ENGLISH)
                val date = inputFormat.parse(dt)
                outputFormat.format(date!!)
            } catch (ex: Exception) {
                dt
            }
        }
    }
}