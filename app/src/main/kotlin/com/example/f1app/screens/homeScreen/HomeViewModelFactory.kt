package com.example.f1app.screens.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.f1app.F1Database
import com.example.f1app.HomeViewModel

class HomeViewModelFactory(private val database: F1Database) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(database) as T
    }
}

