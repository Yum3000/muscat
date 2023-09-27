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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        modifier = Modifier.padding(60.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = "Количество папок: ",
            textAlign = TextAlign.Center,
            color = Color(0, 12, 120), fontSize = 24.sp,
            fontFamily = FontFamily.SansSerif
            )
        Text(text="${data.foldersCount}",
            color = Color(133, 8, 8), fontSize = 20.sp,
            fontFamily = FontFamily.SansSerif,
            fontStyle = FontStyle.Italic
        )

        Text(text = "(${data.artistFoldersCount} с исполнителями, ${data.releaseFoldersCount} с альбомами)",
            color = Color(0, 12, 120), fontSize = 20.sp,
            fontFamily = FontFamily.SansSerif,)
            //textAlign = TextAlign.Center)

        Text(text="Количество элементов: ",
            color = Color(0, 12, 120), fontSize = 24.sp,
            fontFamily = FontFamily.SansSerif,)

        Text(text="${data.itemsInFoldersCount}",
            color = Color(133, 8, 8), fontSize = 20.sp,
            fontFamily = FontFamily.SansSerif,
            fontStyle = FontStyle.Italic
        )

        if (data.biggestFolder.isNotEmpty()) {
            Text(text = "Самая большая папка: ",
                color = Color(0, 12, 120), fontSize = 22.sp,
                fontFamily = FontFamily.SansSerif)
            Text(text="${data.biggestFolder}",
                color = Color(133, 8, 8), fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                fontStyle = FontStyle.Italic
            )
            Text(text = "(${data.biggestFolderItemsCount} элементов)",
                color = Color(0, 12, 120), fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif)

            if (data.biggestFolder != data.smallestFolder) {
                Text(text = "Самая маленькая: ",
                    color = Color(0, 12, 120), fontSize = 22.sp,
                    fontFamily = FontFamily.SansSerif)
                Text("${data.smallestFolder}",
                    color = Color(133, 8, 8), fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontStyle = FontStyle.Italic)

                Text(text="(${data.smallestFolderItemsCount} элементов)",
                    color = Color(0, 12, 120), fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif)
            }
        }

        if (data.ratingsCounts.find { it > 0 } != null) {
            Text(text = "Распределение оценок:",
                color = Color(0, 12, 120), fontSize = 22.sp,
                fontFamily = FontFamily.SansSerif)
            for (i in 0..9) {
                Text(text = "${i + 1} - ${data.ratingsCounts[i]}",
                    color = Color(0, 12, 120), fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif)
            }
        }

        if (data.mostRatedGenres.isNotEmpty()) {
            Text(text = "Жанры с наибольшим количеством оценок: ",
                color = Color(0, 12, 120), fontSize = 22.sp,
                fontFamily = FontFamily.SansSerif)

            Text(text="${data.mostRatedGenres.take(3).joinToString(", ")}",
                color = Color(133, 8, 8), fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                fontStyle = FontStyle.Italic
            )
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