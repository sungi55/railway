package com.sunhurov.home.views

import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sunhurov.model.Station
import com.sunhurov.repository.utils.Resource


object HomeBinding {



    @BindingAdapter("app:showWhenLoading")
    @JvmStatic
    fun showWhenLoading(view: SwipeRefreshLayout, resource: Resource<Boolean>?) {
        if (resource != null) view.isRefreshing = resource.status == Resource.Status.LOADING
    }

    @BindingAdapter("app:showWhenErrorFetching")
    @JvmStatic fun showMessageErrorWhenEmptyResult(view: View, resource: Resource<Boolean>?) {
        if (resource!=null) {
            view.visibility = if ((resource.status == Resource.Status.ERROR)
                && resource.data != null
                && !resource.data!!) View.VISIBLE else View.GONE
        }
    }

    @BindingAdapter("entries")
    @JvmStatic fun bindAdapter(view:AutoCompleteTextView, resource: Resource<List<Station>>?) {
        with(view) {
            resource?.data?.let {
                val adapter = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, it)
                setAdapter(adapter)
            }
        }

    }



}