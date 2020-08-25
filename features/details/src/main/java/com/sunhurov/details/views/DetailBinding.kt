package com.sunhurov.details.views

import android.location.Location
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.sunhurov.model.Station
import com.sunhurov.repository.utils.Resource


object DetailBinding {

    @BindingAdapter("app:startStation")
    @JvmStatic
    fun showStartStation(view: TextView, resource: Resource<Pair<Station,Station>>?) {
        if (resource?.status == Resource.Status.SUCCESS) {
            resource.data?.let {
                view.text = it.first.name
            }
        }
    }

    @BindingAdapter("app:endStation")
    @JvmStatic
    fun showEndStation(view: TextView, resource: Resource<Pair<Station,Station>>?) {
        if (resource?.status == Resource.Status.SUCCESS) {
            resource.data?.let {
                view.text = it.second.name
            }
        }
    }

    @BindingAdapter(value = ["distanceFormatText", "showDistance"], requireAll = true)
    @JvmStatic
    fun showDistanceBetweenStation(view: TextView, formatText:String?, resource: Resource<Pair<Station,Station>>?) {
        if (resource?.status == Resource.Status.SUCCESS && formatText !=null) {
            resource.data?.let {

                val loc1 = Location("")
                if(it.first.latitude != null  && it.first.longitude != null) {
                    loc1.latitude = it.first.latitude!!
                    loc1.longitude = it.first.longitude!!
                }

                val loc2 = Location("")
                if(it.second.latitude != null  && it.second.longitude != null) {
                    loc2.latitude = it.second.latitude!!
                    loc2.longitude = it.second.longitude!!
                }

                var distanceInMeters = 0.0f
                if(loc1.latitude != 0.0 && loc2.latitude != 0.0) {
                    distanceInMeters = loc1.distanceTo(loc2)
                }

                view.text = String.format(formatText, distanceInMeters.toLong()/1000)
            }
        }
    }

}