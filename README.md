[![](https://jitpack.io/v/Shubh0405/location-sdk.svg)](https://jitpack.io/#Shubh0405/location-sdk)

# location-sdk

Fetch user location periodically every few seconds.

## Installation

Add maven jitpack in your project by adding it in your settings.gradle.kts, then add following implementation in your build.gradle

```implementation("com.github.Shubh0405:location-sdk:v1.0.0")```

## Methods to Use

### LocationSharingSDK.startPolling(context, intervalMillis)
Method to start polling of the location, it taken local context and interval as parameters.

### LocationSharingSDK.stopPolling()
For cleaning up the coroutine job created for fetching the location.

### LocationSharingSDK.locationstate
MutableStateFlow<Location?> to fetch the location of the user.
