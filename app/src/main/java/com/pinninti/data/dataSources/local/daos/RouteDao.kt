package com.pinninti.data.dataSources.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pinninti.data.dataSources.local.entities.RoutesEntity

@Dao
interface RouteDao {

    @Query("SELECT * FROM RoutesEntity WHERE id = :id")
    fun getDriverByRouteId(id: Int): RoutesEntity?

    @Query("SELECT * FROM RoutesEntity WHERE type = :routeType")
    fun getDriverByRouteType(routeType: String): List<RoutesEntity>

//    @Query("SELECT * FROM RoutesEntity WHERE type = :routeType LIMIT 1 OFFSET 1")
//    fun getDriverByRouteTypeC(routeType: String): RoutesEntity?
//
//    @Query("SELECT * FROM RoutesEntity WHERE type = :routeType ORDER BY id DESC LIMIT 1 ")
//    fun getDriverByRouteTypeI(routeType: String): RoutesEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(routes: List<RoutesEntity>)
}