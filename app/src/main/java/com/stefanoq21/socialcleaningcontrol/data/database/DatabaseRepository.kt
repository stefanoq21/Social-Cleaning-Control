/*
 *  Copyright 2024 stefanoq21
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

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
                latLng = latLng,
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

    fun getUncleanedLocAndCleanedLocForLastFiveDays(): Flow<List<LocationItem>> {
        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -5)
        val fiveDaysAgo: Date = calendar.time

        return bookDatabase.getLocationDao()
            .getUncleanedLocAndCleanedLocFromDate(fiveDaysAgo)
    }


}