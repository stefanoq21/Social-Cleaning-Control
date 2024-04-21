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

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import java.util.Date

@Entity(tableName = "location_table")
data class LocationItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "location_id")
    val id: Long,
    @ColumnInfo(name = "date")
    val date: Date,
    @ColumnInfo(name = "lat_lng")
    val latLng: LatLng,
    @ColumnInfo(name = "cleaned")
    val cleaned: Boolean,
    @ColumnInfo(name = "description")
    val description: String,
) : ClusterItem {
    override fun getPosition(): LatLng {
        return latLng
    }

    override fun getTitle(): String? {
        return null
    }

    override fun getSnippet(): String? {
        return null
    }

    override fun getZIndex(): Float {
        return 0f
    }

}