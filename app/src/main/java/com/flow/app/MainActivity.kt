package com.flow.app

import android.net.Uri // Import for URL encoding
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.flow.app.ui.navigation.Screen
import com.flow.app.ui.screens.daily_highlight.DailyHighlightScreen
import com.flow.app.ui.screens.daily_highlight.DailyHighlightViewModel
import com.flow.app.ui.screens.dashboard.DashboardScreen // Import DashboardScreen
import com.flow.app.ui.theme.FlowAppTheme
import androidx.lifecycle.viewmodel.compose.viewModel // Import for viewModel()

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
        composable(Screen.DailyHighlight.route) { backStackEntry ->
            val dailyHighlightViewModel: DailyHighlightViewModel = viewModel(backStackEntry) // Use viewModel with backStackEntry
            DailyHighlightScreen(
                viewModel = dailyHighlightViewModel,
                onNavigateToDashboard = {
                    val highlightText = dailyHighlightViewModel.highlightText.value
                    if (highlightText.isNotBlank()) {
                        val encodedHighlight = Uri.encode(highlightText) // URL encode the text
                        navController.navigate(Screen.Dashboard.route.replace("{dailyHighlight}", encodedHighlight))
                    }
                }
            )
        }
        composable(
            route = Screen.Dashboard.route,
            arguments = listOf(navArgument("dailyHighlight") { type = NavType.StringType })
        ) { backStackEntry ->
            val dailyHighlightText = backStackEntry.arguments?.getString("dailyHighlight") ?: ""
            DashboardScreen(
                dailyHighlightText = dailyHighlightText,
                onNavigateToSprint = {
                    navController.navigate(Screen.SprintMode.route)
                }
            )
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