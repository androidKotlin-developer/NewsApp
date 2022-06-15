package com.example.newsapp.util

import android.content.Context
import android.text.format.DateUtils
import android.widget.Toast
import java.sql.Timestamp
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    const val BASE_URL = "https://newsapi.org/v2/"
    const val country_name = "in"
    const val api_key = "185b4546056d48cdabf7e4cfe14a7620"
    const val input_format = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    const val output_format = "yyyy-MM-dd hh:mm:ss"


    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun dateFormat(date: String): CharSequence {
        var dateToReturn = date
        val sdf = SimpleDateFormat(input_format, Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone(TimeZone.getDefault().id)
        val gmt: Date?
        val sdfOutPutToSend = SimpleDateFormat(output_format, Locale.getDefault())
        sdfOutPutToSend.timeZone = TimeZone.getDefault()
        try {
            gmt = sdf.parse(date)
            dateToReturn = sdfOutPutToSend.format(gmt!!)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val timeStamp = Timestamp.valueOf(dateToReturn)

        return DateUtils.getRelativeTimeSpanString(
            timeStamp.time,
            System.currentTimeMillis(),
            DateUtils.MINUTE_IN_MILLIS)
    }
}