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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavHostController
import ru.byum.muscat.R
import ru.byum.muscat.ui.SearchScreen.SearchViewModel


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavHostController
) {
    MainScreen(
        modifier = modifier,
        navController = navController
    )

}

@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 54.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                modifier = Modifier
                    .width(165.dp)
                    .height(45.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Black, Color.Blue)
                        ),
                        shape = RoundedCornerShape(23.dp),
                    ),

                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                onClick = { navController.navigate("search") }) {

                Text(
                    text = "Search ",
                    textAlign = TextAlign.End,
                    color = Color.White,
                    fontSize = 23.sp
                )
                Spacer(modifier = Modifier.size(10.dp))
                Icon(
                    painter = painterResource(id = R.drawable.search_icon),
                    contentDescription = "Search",
                    tint = Color.White,
                    modifier = Modifier.size(45.dp)
                )
            }

        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 54.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier
                    .width(165.dp)
                    .height(45.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors =
                            listOf(Color.Black, Color.Blue)
                        ),
                        shape = RoundedCornerShape(30.dp),
                    ),

                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                onClick = { navController.navigate("folders") }) {
                Text(
                    text = "Folders",
                    textAlign = TextAlign.End,
                    color = Color.White,
                    fontSize = 23.sp
                )
                Spacer(modifier = Modifier.size(10.dp))
                Icon(
                    painter = painterResource(id = R.drawable.folder_icon),
                    contentDescription = "Folders",
                    //tint = Color.White,
                    modifier = Modifier.size(45.dp)
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 54.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier
                    .width(165.dp)
                    .height(45.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors =
                            listOf(Color.Black, Color.Blue)
                        ),
                        shape = RoundedCornerShape(23.dp),
                    ),

                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                onClick = { navController.navigate("stats") }) {

                Text(
                    text = "Stats  ",
                    textAlign = TextAlign.End,
                    color = Color.White,
                    fontSize = 23.sp
                )
                Spacer(modifier = Modifier.size(10.dp))
                Icon(
                    painter = painterResource(id = R.drawable.stats_icon),
                    contentDescription = "Stats",
                    tint = Color.White,
                    modifier = Modifier.size(45.dp)
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 54.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier
                    .width(165.dp)
                    .height(45.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors =
                            listOf(Color.Black, Color.Blue)
                        ),
                        shape = RoundedCornerShape(23.dp),
                    ),

                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                onClick = { navController.navigate("info") }) {

                Text(
                    text = "Info   ",
                    textAlign = TextAlign.End,
                    color = Color.White,
                    fontSize = 23.sp
                )
                Spacer(modifier = Modifier.size(10.dp))
                Icon(
                    imageVector = Icons.Rounded.Info,
                    contentDescription = "Info",
                    tint = Color.White,
                    modifier = Modifier.size(45.dp)
                )
            }
        }
    }
}
