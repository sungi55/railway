package com.sunhurov.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class StationKeyword(
    @PrimaryKey
    @SerializedName("id")
    var id: Int,

    @ColumnInfo(collate = ColumnInfo.NOCASE)
    @SerializedName("keyword")
    var keyword: String? = null,

    @SerializedName("station_id")
    var stationId: Int? = null
)