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

package com.stefanoq21.socialcleaningcontrol.data.database.location

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(locationItem: LocationItem)

    @Delete
    fun delete(locationItem: LocationItem)

    @Query("SELECT * FROM location_table ORDER BY date DESC")
    fun getAllItems(): Flow<List<LocationItem>>

    @Query("SELECT COUNT(*) FROM location_table")
    fun getTotalUncleanedLocations(): Int

    @Query("SELECT COUNT(*) FROM location_table WHERE cleaned = 1")
    fun getTotalCleanedLocations(): Int

    @Query("SELECT * FROM location_table WHERE cleaned = 0 OR date BETWEEN :startDate AND :endDate")
    fun getUncleanedLocAndCleanedLocBetweenDates(
        startDate: Date,
        endDate: Date
    ): Flow<List<LocationItem>>


}