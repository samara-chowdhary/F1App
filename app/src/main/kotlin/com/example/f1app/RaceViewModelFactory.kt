package com.example.f1app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RaceViewModelFactory(
    private val database: F1Database,
    private val trackLocation: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RaceViewModel(database, trackLocation) as T
    }
}

