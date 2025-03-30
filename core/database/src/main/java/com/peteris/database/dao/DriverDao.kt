package com.peteris.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.peteris.database.model.DriverEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DriverDao {

    @Query(value = "SELECT * FROM drivers ORDER BY position ASC")
    suspend fun getDrivers(): List<DriverEntity>

    @Query(value = "SELECT * FROM drivers WHERE id = :id")
    suspend fun getDriver(id: Int): DriverEntity

    @Transaction
    suspend fun deleteOldAndInsertNew(entities: List<DriverEntity>) {
        deleteAllDrivers()
        insertDrivers(entities)
    }

    @Insert
    suspend fun insertDrivers(entities: List<DriverEntity>)

    @Query(value = "DELETE FROM drivers")
    suspend fun deleteAllDrivers()
}