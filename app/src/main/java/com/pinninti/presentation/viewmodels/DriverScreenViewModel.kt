package com.pinninti.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinninti.data.dataSources.local.entities.DriversEntity
import com.pinninti.data.repositories.DriverRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


enum class SortByName {
    FIRSTNAME,
    LASTNAME
}

sealed class DriverUiState {
    object Loading : DriverUiState()
    data class Success(val drivers: List<DriversEntity>) : DriverUiState()
    data class Error(val message: String) : DriverUiState()
}

@HiltViewModel
class DriverScreenViewModel @Inject constructor(private val driverRepository: DriverRepository) :
    ViewModel() {

    var uiState = mutableStateOf<DriverUiState>(DriverUiState.Loading)
        private set

    var sortBy = mutableStateOf(SortByName.FIRSTNAME)
        private set

    init {
        getDrivers(sortBy.value)
    }

    fun getDrivers(sortByName: SortByName) {

        uiState.value = DriverUiState.Loading

        viewModelScope.launch {
            try {
                val drivers: Flow<List<DriversEntity>> = withContext(Dispatchers.IO) {
                    when (sortByName) {
                        SortByName.FIRSTNAME -> driverRepository.getDriversByFirstName()
                        SortByName.LASTNAME -> driverRepository.getDriversByLastName()
                    }
                }
                uiState.value = DriverUiState.Success(drivers.distinctUntilChanged().first())
                sortBy.value = when (sortByName) {
                    SortByName.FIRSTNAME -> SortByName.LASTNAME
                    SortByName.LASTNAME -> SortByName.FIRSTNAME
                }
            } catch (e: Exception) {
                uiState.value =
                    DriverUiState.Error("There is some issue Loading Drivers List. ")
            }
        }
    }


}