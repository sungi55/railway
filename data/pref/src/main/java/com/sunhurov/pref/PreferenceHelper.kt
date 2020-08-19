package com.sunhurov.pref


interface PreferenceHelper {

    fun getLastSyncDate(): Long

    fun setLastSyncDate(date: Long)

}
