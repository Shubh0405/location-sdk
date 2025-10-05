# location-sdk

Fetch user location periodically every few seconds.

### LocationSharingSDK.startPolling(context, intervalMillis)
Method to start polling of the location, it taken local context and interval as parameters.

### LocationSharingSDK.stopPolling()
For cleaning up the coroutine job created for fetching the location.

### LocationSharingSDK.locationstate
MutableStateFlow<Location?> to fetch the location of the user.
