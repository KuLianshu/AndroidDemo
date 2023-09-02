package com.example.lifecycle.observer

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class MyLocationObserver(private val context: Context) : LifecycleEventObserver {

    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener

    companion object{
        private val TAG = "WLY"
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when(event) {
            Lifecycle.Event.ON_CREATE -> {
                startGetLocation()
            }
            Lifecycle.Event.ON_DESTROY ->{
                stopGetLocation()
            }
            else -> {}
        }
    }

    private fun startGetLocation() {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = MyLocationListener()
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000L,1f,locationListener)
    }

    private fun stopGetLocation() {
        Log.i(TAG,"stopGetLocation ==== ")
        locationManager.removeUpdates(locationListener)
    }


    class MyLocationListener : LocationListener {
        override fun onLocationChanged(location: Location) {
            Log.i(TAG, "location changed ${location.toString()}")


        }
    }

}