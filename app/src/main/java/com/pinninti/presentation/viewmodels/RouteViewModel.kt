package com.pinninti.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinninti.data.dataSources.local.entities.RoutesEntity
import com.pinninti.data.repositories.RouteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


sealed class DriverRouteState {
    object Loading : DriverRouteState()
    data class Success(val route: RoutesEntity) : DriverRouteState()
    data class Error(val message: String) : DriverRouteState()
}

@HiltViewModel
class RouteViewModel @Inject constructor(private val routeRepository: RouteRepository) : ViewModel() {

     var driverRouteState = mutableStateOf<DriverRouteState>(DriverRouteState.Loading)
    private  set



    fun getRouteForDriver(driverId: String) {
        driverRouteState.value = DriverRouteState.Loading
        viewModelScope.launch {
            try {
                val route =
                    withContext(Dispatchers.IO) {
                        routeRepository.getRouteForDriver(driverId.toInt())
                    }
                if (route != null) {
                    driverRouteState.value = DriverRouteState.Success(route)
                } else {
                    driverRouteState.value = DriverRouteState.Error("invalid route")
                }
            } catch (e: Exception) {
                driverRouteState.value = DriverRouteState.Error(e.message ?: "There is some error")
            }
        }
    }
}
