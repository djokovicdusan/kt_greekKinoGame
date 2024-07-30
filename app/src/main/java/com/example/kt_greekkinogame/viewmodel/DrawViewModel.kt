package com.example.kt_greekkinogame.viewmodel

import androidx.lifecycle.*
import com.example.kt_greekkinogame.model.Draw
import com.example.kt_greekkinogame.model.DrawResult
import com.example.kt_greekkinogame.repository.DrawRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class DrawViewModel(private val repository: DrawRepository) : ViewModel() {

    private val _draws = MutableLiveData<List<Draw>>()
    val draws: LiveData<List<Draw>> get() = _draws

    private val _results = MutableLiveData<List<DrawResult>>()
    val results: LiveData<List<DrawResult>> get() = _results

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _selectedDates = MutableLiveData<Pair<String, String>>()
    val selectedDates: LiveData<Pair<String, String>> get() = _selectedDates

    init {
        // Initialize with the default date range (last 7 days)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val toDate = sdf.format(Date())
        val fromDate = sdf.format(Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000))
        fetchResults(1100, fromDate, toDate)
    }

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
                val response = repository.getResults(gameId, fromDate, toDate)
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

    fun updateDates(fromDate: String, toDate: String) {
        fetchResults(1100, fromDate, toDate)
    }
}
