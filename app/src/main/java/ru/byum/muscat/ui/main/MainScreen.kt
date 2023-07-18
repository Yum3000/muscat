package ru.byum.muscat.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import androidx.navigation.NavHostController


@Composable
fun MainScreen(modifier: Modifier = Modifier, viewModel: MusicViewModel = hiltViewModel(), navController:NavHostController) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    MainScreen(
        title = state.title,
        released = state.year,
        onGet = viewModel::getRelease,
        modifier = modifier,
        navController = navController
    )

}

@Composable
internal fun MainScreen(
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 54.dp),
            //horizontalArrangement = Arrangement.spacedBy(16.dp)
            horizontalArrangement = Arrangement.Center,
            //verticalAlignment = Alignment.Bottom
        ) {

            Button(
                modifier = Modifier.width(165.dp).height(45.dp).
                background(brush = Brush.verticalGradient(colors =
                listOf(Color.Black, Color.Blue)),
                    shape = RoundedCornerShape(23.dp),),

                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                onClick = { navController.navigate("search") }) {

                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Search",
                    tint = Color.White,
                    modifier = Modifier.size(45.dp)
                )

                //Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                Text(
                    text = "Search",
                    color = Color.White,
                    fontSize = 23.sp
                )
            }

        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 54.dp),
            //horizontalArrangement = Arrangement.spacedBy(16.dp)
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier.width(165.dp).height(45.dp).
                background(brush = Brush.verticalGradient(colors =
                listOf(Color.Black, Color.Blue)),
                    shape = RoundedCornerShape(23.dp),),

                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                onClick = { navController.navigate("second") }) {

                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Second",
                    tint = Color.White,
                    modifier = Modifier.size(45.dp)
                )

                //Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                Text(
                    text = "Second",
                    color = Color.White,
                    fontSize = 23.sp
                )
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 54.dp),
            //horizontalArrangement = Arrangement.spacedBy(16.dp)
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier.width(165.dp).height(45.dp).
                background(brush = Brush.verticalGradient(colors =
                listOf(Color.Black, Color.Blue)),
                    shape = RoundedCornerShape(23.dp),),

                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                onClick = { navController.navigate("stats") }) {


                Icon(
                    imageVector = Icons.Rounded.CheckCircle,
                    contentDescription = "Stats",
                    tint = Color.White,
                    modifier = Modifier.size(45.dp)
                )

                Text(
                    text = "Stats",
                    color = Color.White,
                    fontSize = 23.sp
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 54.dp),
            //horizontalArrangement = Arrangement.spacedBy(16.dp)
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                modifier = Modifier.width(96.dp),
                onClick = { navController.navigate("info") }) {
                //Text("Info Screen")

                Icon(
                    imageVector = Icons.Rounded.Info,
                    contentDescription = "Info",
                    tint = Color(0xFF2EB674),
                    modifier = Modifier.size(96.dp)
                )

                Spacer(modifier = Modifier.width(width = 38.dp))
                Text(
                    text = "Info Screen",
                    color = Color(0xFF2EB674),
                    fontSize = 20.sp
                )
            }
        }
    }
}
