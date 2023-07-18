package ru.byum.muscat.ui.musicScreen

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import androidx.navigation.NavHostController
import ru.byum.muscat.ui.main.MusicViewModel


@Composable
fun MusicScreen(modifier: Modifier = Modifier, viewModel: MusicViewModel = hiltViewModel(), navController:NavHostController) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    MusicScreen(
        title = state.title,
        released = state.year,
        onGet = viewModel::getRelease,
        modifier = modifier,
        navController = navController
    )

}

@Composable
internal fun MusicScreen(
    title: String,
    released: String,
    onGet: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        //var nameMusic by remember { mutableStateOf("Compose") }
        verticalArrangement = Arrangement.Center,

        )
    {

        Text(
            text = "Search Screen",
            color = Color.Green,
            style = androidx.compose.ui.text.TextStyle(textAlign = TextAlign.Center),
            modifier = Modifier.padding(24.dp)
        )

        Button(
            modifier = Modifier.width(196.dp),
            onClick = { onGet() } ){
                Text("Get")
            }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconButton(
                modifier = Modifier.width(96.dp),
                onClick = { navController.navigate("main") }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    modifier = Modifier.size(128.dp),
                    contentDescription = "Back"
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Title: $title")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("year: $released")
        }
    }
}