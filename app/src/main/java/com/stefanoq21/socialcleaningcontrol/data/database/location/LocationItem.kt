package com.stefanoq21.socialcleaningcontrol.data.database.location

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "location_table")
data class LocationItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "location_id")
    val id: Long,
    @ColumnInfo(name = "date")
    val date: Date,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "cleaned")
    val cleaned: Boolean,
    @ColumnInfo(name = "description")
    val description: String,
)