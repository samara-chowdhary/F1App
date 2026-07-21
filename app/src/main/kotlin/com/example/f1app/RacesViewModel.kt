package com.example.f1app

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.f1app.databaseEntities.SessionDao.RaceCalendarRow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RacesViewModel(private val database: F1Database) : ViewModel() {

    private val _races = MutableStateFlow<List<RaceCalendarRow>>(emptyList())
    val races: StateFlow<List<RaceCalendarRow>> = _races

    init {
        Log.d("RACES_VM", "RacesViewModel created")
        loadRaces()
    }

    private fun loadRaces() {
        viewModelScope.launch {
            val result = database.sessionDao().getRacesForYear(2026)
            Log.d("RACES_VM", "Loaded ${result.size} races")
            _races.value = result
        }
    }

}

class RacesViewModelFactory(private val database: F1Database) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RacesViewModel(database) as T
    }
}
