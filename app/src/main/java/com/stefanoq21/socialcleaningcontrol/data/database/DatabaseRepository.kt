package com.stefanoq21.socialcleaningcontrol.data.database


import com.google.android.gms.maps.model.LatLng
import com.stefanoq21.socialcleaningcontrol.data.database.location.LocationItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Date

class DatabaseRepository(
    private val bookDatabase: LocationDatabase,
) {

    suspend fun insertLocation(
        date: Date,
        latLng: LatLng,
        cleaned: Boolean,
        description: String
    ) {
        withContext(Dispatchers.IO) {
            val locationItem = LocationItem(
                id = 0,
                date = date,
                latitude = latLng.latitude,
                longitude = latLng.longitude,
                cleaned = cleaned,
                description = description
            )
            bookDatabase.getLocationDao().insert(locationItem)
        }
    }

    suspend fun replaceLocation(
        locationItem: LocationItem
    ) {
        withContext(Dispatchers.IO) {
            bookDatabase.getLocationDao().insert(locationItem)
        }
    }


    fun getAllItems() = bookDatabase.getLocationDao().getAllItems()

    fun getTotalUncleanedLocations() = bookDatabase.getLocationDao().getTotalUncleanedLocations()

    fun getTotalCleanedLocations() = bookDatabase.getLocationDao().getTotalCleanedLocations()

    suspend fun getUncleanedLocAndCleanedLocForLastFiveDays(): Flow<List<LocationItem>> {
        val dateToday = Date()
        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -5)
        val fiveDaysAgo: Date = calendar.time

        return bookDatabase.getLocationDao()
            .getUncleanedLocAndCleanedLocBetweenDates(dateToday, fiveDaysAgo)
    }


}