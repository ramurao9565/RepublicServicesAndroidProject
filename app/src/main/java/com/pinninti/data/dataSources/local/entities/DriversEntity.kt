package com.pinninti.data.dataSources.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DriversEntity(
    @PrimaryKey val id: String,
    val firstName: String,
    val lastName:String
)
