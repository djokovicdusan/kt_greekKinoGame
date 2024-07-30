package com.example.kt_greekkinogame.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.kt_greekkinogame.model.Draw
import com.example.kt_greekkinogame.model.DrawResult
import com.example.kt_greekkinogame.repository.DrawRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DrawViewModel(private val repository: DrawRepository) : ViewModel() {

    private val _draws = MutableLiveData<List<Draw>>()
    val draws: LiveData<List<Draw>> get() = _draws

    private val _results = MutableLiveData<List<DrawResult>>()
    val results: LiveData<List<DrawResult>> get() = _results

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

    fun fetchResults(gameId: Int, fromDate: String, toDate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getDrawResults(gameId, fromDate, toDate)
                withContext(Dispatchers.Main) {
                    _results.value = response
                    _error.value = null
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message
                }
            }
        }
    }

    fun updateDate(date: String) {
        fetchResults(1100, date, date)
    }
}
