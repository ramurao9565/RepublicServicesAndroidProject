package com.pinninti.data.dataSources.remote.models

import com.pinninti.data.dataSources.local.entities.RoutesEntity

data class RouteDto(
    val id: Int,
    val name: String,
    val type: String
)


fun RouteDto.toRoutesEntity(): RoutesEntity {


    return RoutesEntity(id, name, type)

}