package com.clearcommon.helper.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


object DateTimeUtil {

    val standardTimeZone = "UTC"

    fun dateDifference(
        startDate: Date,
        endDate: Date,
        onClick: ((days: Long, hours: Long, minutes: Long, seconds: Long) -> Unit)
    ) {
        var different = Math.abs(endDate.time - startDate.time)

        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24

        val elapsedDays = different / daysInMilli
        different %= daysInMilli

        val elapsedHours = different / hoursInMilli
        different %= hoursInMilli

        val elapsedMinutes = different / minutesInMilli
        different %= minutesInMilli

        val elapsedSeconds = different / secondsInMilli

        onClick.invoke(elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds)
    }
}

fun String.getMilliseconds(datePattern: String): Long {
    val inputFormat = SimpleDateFormat(datePattern, Locale.getDefault())
    val date = inputFormat.parse(this)
    return date.time
}

fun String.getDate(datePattern: String): Date? {
    val inputFormat = SimpleDateFormat(datePattern, Locale.getDefault())
    try {
        return inputFormat.parse(this)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return null
}

fun Long.getDateString(datePattern: String): String {
    val formatter = SimpleDateFormat(datePattern, Locale.getDefault())

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return formatter.format(calendar.time)
}

fun String.convertDateFromStandardTimeZone(inDatePattern: String,outDatePattern: String): String {
    var outputDateStr = ""
    try {

        val inputFormat = SimpleDateFormat(inDatePattern, Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone(DateTimeUtil.standardTimeZone)

        val outputFormat = SimpleDateFormat(outDatePattern, Locale.getDefault())
        outputFormat.timeZone = TimeZone.getDefault()

        val date = inputFormat.parse(this)
        if (date != null) {
            outputDateStr = outputFormat.format(date)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return outputDateStr
}

fun String.convertDateToStandardTimeZone(datePattern: String): String {
    var outputDateStr = ""
    try {

        val inputFormat = SimpleDateFormat(datePattern, Locale.getDefault())
        inputFormat.timeZone = TimeZone.getDefault()

        val outputFormat = SimpleDateFormat(datePattern, Locale.getDefault())
        outputFormat.timeZone = TimeZone.getTimeZone(DateTimeUtil.standardTimeZone)

        val date = inputFormat.parse(this)
        if (date != null) {
            outputDateStr = outputFormat.format(date)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return outputDateStr
}

fun String.changeDatePattern(inputPattern: String, outputPattern: String): String {
    var outputDateStr = ""
    try {

        val inputFormat = SimpleDateFormat(inputPattern, Locale.getDefault())

        val outputFormat = SimpleDateFormat(outputPattern, Locale.getDefault())

        val date = inputFormat.parse(this)
        if (date != null) {
            outputDateStr = outputFormat.format(date)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return outputDateStr
}

fun getAgoTimeString(dateMillis: Long): String {
    val timeScale = HashMap<String, Int>()
    timeScale["sec"] = 1
    timeScale["min"] = 60
    timeScale["hour"] = 3600
    timeScale["day"] = 86400
    timeScale["week"] = 605800
    timeScale["month"] = 2629743
    timeScale["year"] = 31556926
    var scale = ""
    val timeAgo =
        TimeUnit.MILLISECONDS.toSeconds(Calendar.getInstance().timeInMillis) - TimeUnit.MILLISECONDS.toSeconds(
            dateMillis
        )
    if (timeAgo < 60) {
        scale = "sec"
        return "Just now"
    } else if (timeAgo < 3600) {
        scale = "min"
    } else if (timeAgo < 86400) {
        scale = "hour"
    } else if (timeAgo < 172800) {
        return "Yesterday"
    } else if (timeAgo < 605800) {
        scale = "day"
    } else if (timeAgo < 2629743) {
        scale = "week"
    } else if (timeAgo < 31556926) {
        scale = "month"
    } else {
        scale = "year"
    }
    val ago = timeAgo / timeScale[scale]!!
    var s = ""
    if (ago > 1) {
        s = "s"
    }

    return ago.toString() + " " + scale + "" + s + " " + "ago"
}
