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
    suspend fun getUncleanedLocAndCleanedLocBetweenDates(
        startDate: Date,
        endDate: Date
    ): Flow<List<LocationItem>>


}