package com.pinninti.data.dataSources.remote.models

data class DriverRouteResponse(
    val drivers: List<DriverDto>,
    val routes: List<RouteDto>
)