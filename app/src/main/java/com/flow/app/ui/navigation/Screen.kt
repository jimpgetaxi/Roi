package com.flow.app.ui.navigation

sealed class Screen(val route: String) {
    object DailyHighlight : Screen("daily_highlight_route")
    object Dashboard : Screen("dashboard_route")
    object SprintMode : Screen("sprint_mode_route")
    object Settings : Screen("settings_route")

    // Utility function to build routes with arguments if needed later
    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}