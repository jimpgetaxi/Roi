package com.flow.app.ui.screens.sprint_mode

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flow.app.R
import com.flow.app.ui.theme.FlowAppTheme

@Composable
fun SprintModeScreen(
    onSprintFinished: () -> Unit,
    viewModel: SprintModeViewModel = viewModel()
) {
    val timeRemaining by viewModel.timeRemaining.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()
    val sprintDuration by viewModel.sprintDuration.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.sprint_mode_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(64.dp))

        // Timer Display
        val minutes = timeRemaining / 1000 / 60
        val seconds = (timeRemaining / 1000) % 60
        val timerText = String.format("%02d:%02d", minutes, seconds)

        Text(
            text = timerText,
            style = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground
        )

        if (timeRemaining == 0L && !isRunning) {
            Text(
                text = stringResource(R.string.sprint_finished_message),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(64.dp))

        // Control Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!isRunning && timeRemaining == 0L) { // Initial state or after stop/finish
                Button(
                    onClick = { viewModel.startSprint() },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(stringResource(R.string.start_sprint_button))
                }
            } else if (isRunning) { // Sprint is running
                Button(
                    onClick = { viewModel.pauseSprint() },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text(stringResource(R.string.pause_sprint_button))
                }
                Button(
                    onClick = {
                        viewModel.stopSprint()
                        onSprintFinished()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(stringResource(R.string.stop_sprint_button))
                }
            } else { // Sprint is paused
                Button(
                    onClick = { viewModel.resumeSprint() },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(stringResource(R.string.resume_sprint_button))
                }
                Button(
                    onClick = {
                        viewModel.stopSprint()
                        onSprintFinished()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(stringResource(R.string.stop_sprint_button))
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Duration selection
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.select_duration_label),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            // Changed from Row to FlowRow for better layout on smaller screens
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.spacedBy(8.dp) // Spacing between rows/items
            ) {
                listOf(15L, 25L, 45L, 90L).forEach { duration ->
                    Button(
                        onClick = { viewModel.setSprintDuration(duration) },
                        enabled = !isRunning, // Cannot change duration while sprint is running
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (sprintDuration == duration * 60 * 1000L) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = if (sprintDuration == duration * 60 * 1000L) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer
                        ),
                        modifier = Modifier.padding(horizontal = 4.dp) // Small horizontal padding for buttons
                    ) {
                        Text(stringResource(R.string.minutes_label, duration))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SprintModeScreenPreview() {
    FlowAppTheme {
        SprintModeScreen(onSprintFinished = {})
    }
}