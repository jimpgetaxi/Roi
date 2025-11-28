package com.flow.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flow.app.ui.navigation.Screen
import com.flow.app.ui.screens.daily_highlight.DailyHighlightScreen // Import the new DailyHighlightScreen
import com.flow.app.ui.theme.FlowAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlowAppTheme {
                val navController = rememberNavController()
                
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.DailyHighlight.route,
        modifier = modifier
    ) {
        composable(Screen.DailyHighlight.route) {
            DailyHighlightScreen(
                onNavigateToDashboard = {
                    navController.navigate(Screen.Dashboard.route)
                }
            )
        }
        composable(Screen.Dashboard.route) {
            ScreenPlaceholder(title = "Dashboard Screen")
        }
        composable(Screen.SprintMode.route) {
            ScreenPlaceholder(title = "Sprint Mode Screen")
        }
        composable(Screen.Settings.route) {
            ScreenPlaceholder(title = "Settings Screen")
        }
    }
}

@Composable
fun ScreenPlaceholder(title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}