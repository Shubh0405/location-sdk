package com.example.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object LocationSharingSDK {

    var locationstate = MutableStateFlow<Location?>(null)
        private set

    private var pollingJob: Job? = null

    @SuppressLint("MissingPermission")
    fun startPolling(context: Context, intervalMillis: Long) {
        if (pollingJob != null) return // Already polling

        val fusedClient = LocationServices.getFusedLocationProviderClient(context)

        pollingJob = CoroutineScope(Dispatchers.IO).launch @androidx.annotation.RequiresPermission(
            allOf = [android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION]
        ) {
            while(true) {
                try {
                    val location: Location? = getLastLocationSuspend(fusedClient)
                    Log.d("LocationSharingSDK", "Fetched location: $location")
                    locationstate.emit(location)
                } catch (e: Exception) {
                    e.printStackTrace()
                    pollingJob?.cancel()
                }
                delay(intervalMillis)
            }
        }
    }


    @SuppressLint("MissingPermission")
    private suspend fun getLastLocationSuspend(fusedClient: FusedLocationProviderClient): Location? =
        suspendCancellableCoroutine { cont ->
            fusedClient.lastLocation
                .addOnSuccessListener { location ->
                    cont.resume(location) // success
                }
                .addOnFailureListener { e ->
                    cont.resumeWithException(e) // error
                }
        }


    fun stopPolling() {
        Log.d("LocationSharingSDK", "Stopping location polling")
        pollingJob?.cancel()
        pollingJob = null
    }

}