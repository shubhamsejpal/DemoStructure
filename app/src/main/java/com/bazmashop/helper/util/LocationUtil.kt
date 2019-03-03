package com.clearcommon.helper.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.util.*

object LocationUtil {
}

fun String.getLatLng(context: Context): LatLng? {

    val geocoder = Geocoder(context)
    var addressList: List<Address>? = null

    try {
        addressList = geocoder.getFromLocationName(this, 1)
    } catch (e: IOException) {
        e.printStackTrace()
    }

    val location = addressList!![0]
    location.latitude
    location.longitude


    return LatLng(
        location.latitude,
        location.longitude
    )
}

fun LatLng.showMap(context: Activity, title: String) {
    val geoLocation =
        Uri.parse("geo:${this.latitude},${this.longitude}?q=${this.latitude},${this.longitude}($title)")
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = geoLocation
    if (intent.resolveActivity(context.packageManager) != null)
        context.startActivity(intent)
    else
        "You don't have map installed".logD()
}

fun LatLng.getAddress(context: Context): Address? {
    val geocoder = Geocoder(context, Locale.getDefault())
    try {
        val addresses = geocoder.getFromLocation(this.latitude, this.longitude, 1)
        var obj: Address? = null
        if (addresses != null && addresses.size > 0)
            obj = addresses[0]
        return obj
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return null
}
