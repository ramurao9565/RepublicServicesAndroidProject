package com.pinninti.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.pinninti.R
import com.pinninti.presentation.viewmodels.DriverScreenViewModel
import com.pinninti.presentation.viewmodels.DriverUiState
import com.pinninti.presentation.viewmodels.SortByName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun DriversScreen(
    viewModel: DriverScreenViewModel = viewModel(),
    navController: NavController
) {

    val state by viewModel.uiState
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val sortByName by viewModel.sortBy
    


    Scaffold(topBar = {
        TopAppBar(
            title = { Text(stringResource(R.string.driversTopBarString)) },
            actions = {
                toolBarAction(scope, viewModel, sortByName)
            },
        )
    }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            state.let { uiState ->
                when (uiState) {
                    is DriverUiState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(30.dp)
                                .align(Alignment.Center)
                        )
                    }
                    is DriverUiState.Success -> {

                        

                        LaunchedEffect(uiState.drivers) {
                            listState.scrollToItem(0, 0)
                        }
//
//
                        LazyColumn(state = listState, modifier = Modifier.padding(8.dp)) {
                            items(uiState.drivers, key = { it.id }) { entity ->
                                Text(text = "${entity.firstName} ${entity.lastName}",
                                    modifier = Modifier
                                        .clickable {
                                            navController.navigate("routeScreen/${entity.id}")
                                        }
                                        .fillMaxSize()
                                        .padding(16.dp))
                                Divider(
                                    modifier = Modifier.padding(vertical = 4.dp), thickness = 2.dp
                                )
                            }
                        }

                    }
                    is DriverUiState.Error -> {
                        Text(
                            text = uiState.message,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(
                                Alignment.Center
                            )
                        )
                    }
                }

            }
        }
    }
}

@Composable
private fun toolBarAction(
    scope: CoroutineScope,
    viewModel: DriverScreenViewModel,
    sortByName: SortByName
) {
    Button(onClick = {
        scope.launch {
            viewModel.getDrivers(sortByName)
        }
    }, modifier = Modifier.padding(8.dp)) {
        Text(text = "Sort By ${sortByName.name}")
    }
}