package com.sunhurov.pref

import android.content.SharedPreferences


class AppPreferencesHelper(private val mPrefs: SharedPreferences) : PreferenceHelper {

    companion object {
        private const val PREF_KEY_LAST_SYNC = "PREF_KEY_LAST_SYNC"
    }

    override fun setLastSyncDate(date: Long) {
        mPrefs.edit().putLong(PREF_KEY_LAST_SYNC, date).apply()
    }

    override fun getLastSyncDate(): Long =  mPrefs.getLong(PREF_KEY_LAST_SYNC, 0)

}
