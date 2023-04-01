package com.pinninti.data.dataSources.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoutesEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val type: String
)
