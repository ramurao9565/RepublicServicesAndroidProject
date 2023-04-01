package com.pinninti.data.dataSources.remote.models

import com.pinninti.data.dataSources.local.entities.DriversEntity

data class DriverDto(
    val id: String,
    val name: String
)

fun DriverDto.toDriverEntity(): DriversEntity {

    val nameArray = name.split(" ")



    return DriversEntity(id, nameArray.getOrNull(0) ?: "", nameArray.getOrNull(1) ?: "")

}