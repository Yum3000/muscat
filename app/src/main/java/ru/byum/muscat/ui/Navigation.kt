package ru.byum.muscat.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.byum.muscat.ui.main.InfoScreen
import ru.byum.muscat.ui.main.MainScreen
import ru.byum.muscat.ui.main.MusicScreen
import ru.byum.muscat.ui.main.SecondScreen
import ru.byum.muscat.ui.main.StatsScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavigation(navController: NavHostController) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(modifier = Modifier.padding(16.dp), navController = navController) }
        composable("search") {MusicScreen(modifier = Modifier.padding(16.dp), navController = navController)}
        composable("second") {SecondScreen(modifier = Modifier.padding(16.dp), navController) }
        composable("info") {InfoScreen(modifier = Modifier.padding(16.dp), navController) }
        composable("stats") {StatsScreen(modifier = Modifier.padding(16.dp), navController) }
        // TODO: Add more destinations
    }
}
