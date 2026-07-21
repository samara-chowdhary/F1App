package com.example.f1app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChampsViewModelFactory(
    private val database: F1Database
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChampsViewModel::class.java)) {
            return ChampsViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}