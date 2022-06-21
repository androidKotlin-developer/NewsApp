package com.example.newsapp.util

import android.content.Context
import android.text.format.DateUtils
import android.widget.Toast
import com.example.quizapp.utill.Constant.input_format
import com.example.quizapp.utill.Constant.output_format
import java.sql.Timestamp
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utill {

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

    fun nullChecker(string: String?): String? {
        var returnString: String? = ""
        if (string != null && string.isNotEmpty() && !string.contains("null")) {
            returnString = string
        }
        return returnString
    }



}