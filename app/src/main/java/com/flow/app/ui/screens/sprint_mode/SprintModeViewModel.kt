package com.flow.app.ui.screens.sprint_mode

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SprintModeViewModel : ViewModel() {

    private val _timeRemaining = MutableStateFlow(0L) // Time in milliseconds
    val timeRemaining: StateFlow<Long> = _timeRemaining.asStateFlow()

    private val _sprintDuration = MutableStateFlow(25 * 60 * 1000L) // Default 25 minutes in ms
    val sprintDuration: StateFlow<Long> = _sprintDuration.asStateFlow()

    private var countDownTimer: CountDownTimer? = null

    // State of the timer (e.g., Running, Paused, Stopped)
    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    fun startSprint() {
        if (!_isRunning.value) {
            _isRunning.value = true
            countDownTimer = object : CountDownTimer(_sprintDuration.value, 1000L) {
                override fun onTick(millisUntilFinished: Long) {
                    _timeRemaining.value = millisUntilFinished
                }

                override fun onFinish() {
                    _timeRemaining.value = 0L
                    _isRunning.value = false
                    // Handle sprint finish (e.g., show notification, navigate)
                }
            }.start()
        }
    }

    fun pauseSprint() {
        countDownTimer?.cancel()
        _isRunning.value = false
    }

    fun resumeSprint() {
        // Need to create a new timer from _timeRemaining
        if (!_isRunning.value && _timeRemaining.value > 0) {
            _isRunning.value = true
            countDownTimer = object : CountDownTimer(_timeRemaining.value, 1000L) {
                override fun onTick(millisUntilFinished: Long) {
                    _timeRemaining.value = millisUntilFinished
                }

                override fun onFinish() {
                    _timeRemaining.value = 0L
                    _isRunning.value = false
                    // Handle sprint finish
                }
            }.start()
        }
    }

    fun stopSprint() {
        countDownTimer?.cancel()
        _isRunning.value = false
        _timeRemaining.value = 0L
    }

    fun setSprintDuration(durationMinutes: Long) {
        _sprintDuration.value = durationMinutes * 60 * 1000L
        _timeRemaining.value = _sprintDuration.value // Reset time when duration changes
        if (_isRunning.value) { // If running, restart timer with new duration
            pauseSprint()
            startSprint()
        }
    }

    override fun onCleared() {
        super.onCleared()
        countDownTimer?.cancel()
    }
}