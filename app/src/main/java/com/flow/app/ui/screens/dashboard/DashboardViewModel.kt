package com.flow.app.ui.screens.dashboard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DashboardViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _dailyHighlight = MutableStateFlow(savedStateHandle.get<String>("dailyHighlight") ?: "")
    val dailyHighlight: StateFlow<String> = _dailyHighlight.asStateFlow()

    // Function to update daily highlight, useful if we edit it from dashboard later
    fun updateDailyHighlight(newHighlight: String) {
        _dailyHighlight.value = newHighlight
        savedStateHandle["dailyHighlight"] = newHighlight
    }
}