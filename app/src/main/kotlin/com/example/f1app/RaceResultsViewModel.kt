package com.example.f1app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.f1app.databaseEntities.DriverDao.RaceResultRow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class RaceResultsState(
    val results: List<RaceResultRow> = emptyList(),
    val isLoading: Boolean = true,
    val hasData: Boolean = false
)

class RaceResultsViewModel(
    private val database: F1Database,
    private val location: String,
    private val year: Int
) : ViewModel() {

    private val _state = MutableStateFlow(RaceResultsState())
    val state: StateFlow<RaceResultsState> = _state

    init {
        loadResults()
    }

    private fun loadResults() {
        viewModelScope.launch {
            val results = database.driverDao().getRaceResults(location, year)
            _state.value = RaceResultsState(
                results = results,
                isLoading = false,
                hasData = results.isNotEmpty()
            )
        }
    }
}

class RaceResultsViewModelFactory(
    private val database: F1Database,
    private val location: String,
    private val year: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RaceResultsViewModel(database, location, year) as T
    }
}

