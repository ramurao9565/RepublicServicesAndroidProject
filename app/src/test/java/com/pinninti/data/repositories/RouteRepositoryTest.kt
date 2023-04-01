package com.pinninti.data.repositories

import com.pinninti.data.dataSources.local.daos.RouteDao
import com.pinninti.data.dataSources.local.entities.RoutesEntity
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

//@RunWith(MockitoJUnitRunner::class)
class RouteRepositoryTest {


    @MockK
    lateinit var routeDao: RouteDao

    lateinit var routeRepository: RouteRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        routeRepository = RouteRepository(routeDao)
    }

    @Test
    fun `when driver id is the same as the route id`() {
        // Given

        val driverId = 1

        val routeId = 1
        val routesEntityOne = RoutesEntity(routeId, "Driver A", "R")

        every { routeDao.getDriverByRouteId(driverId) } returns routesEntityOne

        // When
        val result = routeRepository.getRouteForDriver(driverId)

        // Then
        assertEquals(routesEntityOne, result)
        verify(exactly = 1) { routeDao.getDriverByRouteId(driverId) }
    }

    @Test
    fun `when driver id is divisible by 2`() {
        // Given
        val driverId = 8

        val routeType = "R"
        every { routeDao.getDriverByRouteId(driverId) } returns null

        val routesEntityOne = RoutesEntity(8, "Route A", routeType)
        val routesEntityTwo = RoutesEntity(9, "Route B", routeType)
        every { routeDao.getDriverByRouteType(routeType) } returns listOf(routesEntityOne,routesEntityTwo)

        // When
        val result = routeRepository.getRouteForDriver(driverId)

        // Then
        assertEquals(routesEntityOne, result)
        verify(exactly = 1) { routeDao.getDriverByRouteType(routeType) }
        verify(exactly = 1) { routeDao.getDriverByRouteId(driverId) }
    }

    @Test
    fun `when driver id is divisible by 5`() {
        // Given
        val driverId = 15

        val routeType = "C"
        every { routeDao.getDriverByRouteId(driverId) } returns null

        val routesEntityFirstC = RoutesEntity(8, "Route C Type 1", routeType)
        val routesEntitySecondC = RoutesEntity(9, "Route C Type 2", routeType)
        every { routeDao.getDriverByRouteType(routeType) } returns listOf(
            routesEntityFirstC,
            routesEntitySecondC
        )

        // When
        val result = routeRepository.getRouteForDriver(driverId)

        // Then
        assertEquals(routesEntitySecondC, result)
        verify(exactly = 1) { routeDao.getDriverByRouteType(routeType) }
        verify(exactly = 1) { routeDao.getDriverByRouteId(driverId) }
    }

    @Test
    fun `when driver id doesn't meet any of the above conditions`() {
        // Given
        val driverId = 13

        val routeType = "I"
        every { routeDao.getDriverByRouteId(driverId) } returns null

        val routesEntityFirstI = RoutesEntity(8, "Route I Type 1", routeType)
        val routesEntitySecondI = RoutesEntity(9, "Route I Type 2", routeType)

        every { routeDao.getDriverByRouteType(routeType) } returns listOf(
            routesEntityFirstI,
            routesEntitySecondI
        )

        // When
        val result = routeRepository.getRouteForDriver(driverId)

        // Then
        assertEquals(routesEntitySecondI, result)
        verify(exactly = 1) { routeDao.getDriverByRouteType(routeType) }
        verify(exactly = 1) { routeDao.getDriverByRouteId(driverId) }
    }
}



