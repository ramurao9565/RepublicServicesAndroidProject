package com.pinninti.data.repositories

import com.pinninti.data.dataSources.local.daos.RouteDao
import com.pinninti.data.dataSources.local.entities.RoutesEntity
import javax.inject.Inject


class RouteRepository @Inject constructor( private val routeDao:RouteDao) {
    fun getRouteForDriver(driverId: Int): RoutesEntity? {
        val routesEntity = routeDao.getDriverByRouteId(driverId)
        return when {
            // if driver id is the same as the route id
            driverId == routesEntity?.id -> routesEntity
            // if driver id is divisible by 2 call first route type R
            driverId % 2 == 0 -> routeDao.getDriverByRouteType("R").first()
            driverId % 5 == 0 -> {
                // if driver id is divisible by 5 call 2nd route type C
                routeDao.getDriverByRouteType("C").component2()
            }
            else -> {
                // if driver id doesn't meet any of the above conditions then call last route type I
                routeDao.getDriverByRouteType("I").last()
            }
        }
    }
}
