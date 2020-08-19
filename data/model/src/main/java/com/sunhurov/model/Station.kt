package com.sunhurov.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Station(

    @PrimaryKey
    @SerializedName("id")
    var id: Int,

    @SerializedName("city")
    var city: String? = null,

    @SerializedName("country")
    var country: String? = null,

    @SerializedName("hits")
    var hits: Int? = null,

    @SerializedName("ibnr")
    var ibnr: Int? = null,

    @SerializedName("latitude")
    var latitude: Double? = null,

    @SerializedName("localised_name")
    var localisedName: String? = null,

    @SerializedName("longitude")
    var longitude: Double? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("name_slug")
    var nameSlug: String? = null,

    @SerializedName("region")
    var region: String? = null
) {
    override fun toString(): String {
        return "$name"
    }
}