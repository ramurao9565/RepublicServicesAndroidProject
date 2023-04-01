package com.pinninti.data.dataSources.local.daos

import androidx.room.*
import com.pinninti.data.dataSources.local.entities.DriversEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface DriverDao {
    @Query("SELECT * FROM DriversEntity ORDER BY firstName ASC")
    fun getDriversByFirstName(): Flow<List<DriversEntity>>

    @Query("SELECT * FROM DriversEntity ORDER BY lastName ASC")
    fun getDriversByLastName(): Flow<List<DriversEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(drivers: List<DriversEntity>)

}