package ru.cft.shift2023winter.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.cft.shift2023winter.presentation.compose.LocationScreen
import ru.cft.shift2023winter.presentation.compose.WeatherScreen

@Composable
fun WeatherNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "weather"
) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = startDestination
    ) {
        composable("weather") {
            WeatherScreen({  navController.popBackStack();navController.navigate("location_selection") },
                { navController.navigate("location_selection") })
        }
        composable("location_selection") {
            LocationScreen({  navController.popBackStack();navController.navigate("weather") })
        }
    }
}