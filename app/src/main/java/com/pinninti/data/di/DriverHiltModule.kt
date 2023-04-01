package com.pinninti.data.di

import android.content.Context
import androidx.room.Room
import com.pinninti.data.dataSources.local.Database
import com.pinninti.data.dataSources.local.daos.DriverDao
import com.pinninti.data.dataSources.local.daos.RouteDao
import com.pinninti.data.dataSources.remote.DriverRouteAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DriverHiltModule {

    @Singleton
    @Provides
    fun getDB(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(context, Database::class.java, DB_NAME).build()
    }

    @Singleton
    @Provides
    fun getDriverApi(): DriverRouteAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
                // TODO Use moshi instead of Gson  Moshi doesn't use reflection
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(DriverRouteAPI::class.java)
    }


    @Singleton
    @Provides
    fun getRouteDao(database: Database): RouteDao {
        return database.routesDao()
    }

    @Singleton
    @Provides
    fun getDriverDao(database: Database): DriverDao {
        return database.driverDao()
    }

    companion object {
        private const val DB_NAME = "driver_app_db"
        private const val BASE_URL = "https://d49c3a78-a4f2-437d-bf72-569334dea17c.mock.pstmn.io"
    }
}