package com.peteris.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.peteris.database.dao.DriverDao
import com.peteris.database.model.DriverEntity

@Database(
    entities = [DriverEntity::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun driverDao(): DriverDao
}