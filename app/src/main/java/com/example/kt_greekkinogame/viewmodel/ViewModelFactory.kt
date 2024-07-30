package com.example.kt_greekkinogame.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kt_greekkinogame.repository.DrawRepository

class ViewModelFactory(private val repository: DrawRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DrawViewModel::class.java)) {
            return DrawViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
