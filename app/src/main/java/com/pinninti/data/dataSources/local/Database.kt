package com.pinninti.data.dataSources.local

import androidx.room.RoomDatabase
import com.pinninti.data.dataSources.local.entities.DriversEntity
import com.pinninti.data.dataSources.local.entities.RoutesEntity
import com.pinninti.data.dataSources.local.daos.DriverDao
import com.pinninti.data.dataSources.local.daos.RouteDao

@androidx.room.Database(entities = [DriversEntity::class, RoutesEntity::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract fun driverDao(): DriverDao

    abstract fun routesDao():RouteDao
}