package com.example.kt_greekkinogame.viewmodel

import androidx.lifecycle.*
import com.example.kt_greekkinogame.model.Draw
import com.example.kt_greekkinogame.repository.DrawRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DrawViewModel(private val repository: DrawRepository) : ViewModel() {

    private val _draws = MutableLiveData<List<Draw>>()
    val draws: LiveData<List<Draw>> get() = _draws

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun fetchUpcomingDraws(gameId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getUpcomingDraws(gameId)
                withContext(Dispatchers.Main) {
                    _draws.value = response
                    _error.value = null
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message
                }
            }
        }
    }
}
