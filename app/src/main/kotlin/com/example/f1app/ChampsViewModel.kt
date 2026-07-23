package com.example.f1app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1app.databaseEntities.DriverDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChampsViewModel(
    private val database: F1Database
) : ViewModel() {

    private val _standings = MutableStateFlow(emptyList<DriverDao.ChampionshipRow>())
    val standings: StateFlow<List<DriverDao.ChampionshipRow>> = _standings

    private val _constructorStandings = MutableStateFlow(emptyList<DriverDao.ConstructorsChampionshipRow>())
    // Expose this as constructorStandings (or constructorsStandings) to match your Screen
    val constructorStandings: StateFlow<List<DriverDao.ConstructorsChampionshipRow>> = _constructorStandings

    init {
        loadStandings()
    }

    private fun loadStandings() {
        viewModelScope.launch {
            _standings.value = database.driverDao().getCurrentDriversChampionship()
        }

        viewModelScope.launch {
            _constructorStandings.value = database.driverDao().getCurrentConstructorsChampionship()
        }
    }
}

