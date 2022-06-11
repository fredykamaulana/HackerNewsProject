package com.fmyapp.storylist.presentation.utils

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun Long.convertEpochSecond(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val dateFormatter = DateTimeFormatter.ISO_INSTANT
        dateFormatter.format(Instant.ofEpochSecond(this))
    } else {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val date = Date(this * 1000)
        sdf.format(date)
    }
}

@Suppress("Deprecation")
fun String.readHtml(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT).toString()
    } else {
        Html.fromHtml(this).toString()
    }
}