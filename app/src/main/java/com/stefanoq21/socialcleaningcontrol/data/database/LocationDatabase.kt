package com.stefanoq21.socialcleaningcontrol.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.stefanoq21.socialcleaningcontrol.data.Constants
import com.stefanoq21.socialcleaningcontrol.data.database.location.LocationDao
import com.stefanoq21.socialcleaningcontrol.data.database.location.LocationItem

@Database(
    entities = [LocationItem::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class LocationDatabase : RoomDatabase() {

    abstract fun getLocationDao(): LocationDao

    companion object {

        @Volatile
        private var INSTANCE: LocationDatabase? = null

        fun getInstance(context: Context): LocationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocationDatabase::class.java,
                    Constants.databaseName
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}