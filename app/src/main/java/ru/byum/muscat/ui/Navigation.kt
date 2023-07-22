package ru.byum.muscat.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.byum.muscat.ui.infoScreen.InfoScreen
import ru.byum.muscat.ui.main.MainScreen
import ru.byum.muscat.ui.musicScreen.MusicScreen
import ru.byum.muscat.ui.secondScreen.SecondScreen
import ru.byum.muscat.ui.statsScreen.StatsScreen
import ru.byum.muscat.ui.artistScreen.ArtistScreen
import ru.byum.muscat.ui.musicScreen.MusicViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavigation(navController: NavHostController) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(modifier = Modifier.padding(16.dp), navController = navController) }
        composable("search") { MusicScreen(modifier = Modifier.padding(16.dp), navController = navController) }
        composable("second") { SecondScreen(modifier = Modifier.padding(16.dp), navController) }
        composable("info") { InfoScreen(modifier = Modifier.padding(16.dp), navController) }
        composable("stats") { StatsScreen(modifier = Modifier.padding(16.dp), navController) }
        composable("artistScreen/{artist_id}") {backStackEntry ->
            ArtistScreen(
                backStackEntry.arguments?.getString("artist_id")!!,
                navController,
                modifier = Modifier.padding(16.dp),
            )
        }
    }

}
