package ru.byum.muscat.ui.statsScreen


import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ru.byum.muscat.data.local.di.DatabaseModule
import ru.byum.muscat.data.local.di.DatabaseModule_ProvideAppDatabaseFactory
import java.time.format.TextStyle

@OptIn(ExperimentalMaterial3Api::class, InternalComposeApi::class)
@Composable
fun StatsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: StatsScreenViewModel = hiltViewModel(),
) {
    viewModel.getData()
    val data = viewModel.data.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Всего папок ${data.foldersCount} c ${data.itemsInFoldersCount} элементами")

        Text(text = " (${data.artistFoldersCount} с исполнителями, ${data.releaseFoldersCount} с альбомами.)")

        if (data.biggestFolder.isNotEmpty()) {
            Text(text = "Самая большая папка - ${data.biggestFolder} (${data.biggestFolderItemsCount} элементов).")
            if (data.biggestFolder != data.smallestFolder) {
                Text(text = "Самая маленькая - ${data.smallestFolder} (${data.smallestFolderItemsCount} элементов).")
            }
        }

        if (data.ratingsCounts.find { it > 0 } != null) {
            Text(text = "Распределение оценок:")
            for (i in 0..9) {
                Text(text = "${i + 1} - ${data.ratingsCounts[i]}")
            }
        }

        if (data.mostRatedGenres.isNotEmpty()) {
            Text(text = "Жанры с большим количеством оценок: ${data.mostRatedGenres.take(3).joinToString(", ")}")
        }

    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        IconButton(modifier = Modifier.width(96.dp), onClick = { navController.popBackStack() }) {
            Icon(
                Icons.Filled.ArrowBack,
                modifier = Modifier.size(128.dp),
                contentDescription = "Back"
            )
        }
    }
}