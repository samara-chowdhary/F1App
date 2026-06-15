package com.example.f1app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1app.databaseEntities.Meeting
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeViewModel(private val database: F1Database) : ViewModel() {

    private val _nextRace = MutableStateFlow<Meeting?>(null)
    val nextRace: StateFlow<Meeting?> = _nextRace

    init {
        loadNextRace()
    }

    private fun loadNextRace() {
        viewModelScope.launch {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val currentDate = sdf.format(Date())
            _nextRace.value = database.sessionDao().getNextRaceMeeting(currentDate)
        }
    }
}

