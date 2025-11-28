package com.flow.app.ui.screens.daily_highlight

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DailyHighlightViewModel : ViewModel() {

    private val _highlightText = MutableStateFlow("")
    val highlightText: StateFlow<String> = _highlightText.asStateFlow()

    fun onHighlightChange(newText: String) {
        _highlightText.value = newText
    }

    fun onFocusClick() {
        // Logic to start focus or navigate to dashboard will be handled here or in UI
        // For now, we just keep the state ready
    }
}