package com.sunhurov.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sunhurov.local.dao.StationKeywordDao
import com.sunhurov.local.dao.StationDao
import com.sunhurov.model.*

@Database(entities = [
    Station::class,
    StationKeyword::class
], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    // DAO
    abstract fun stationDao(): StationDao

    abstract fun stationKeywordDao(): StationKeywordDao


    companion object {

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "RailwayApp.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}