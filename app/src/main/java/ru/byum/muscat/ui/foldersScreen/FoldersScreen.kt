package ru.byum.muscat.ui.foldersScreen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.byum.muscat.R
import ru.byum.muscat.ui.artistScreen.ArtistReleasesList


@OptIn(ExperimentalMaterial3Api::class, InternalComposeApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FoldersScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: FolderViewModel = hiltViewModel(),
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.width(96.dp),
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            modifier = Modifier.size(128.dp),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.CreateFolder() },
                containerColor = Color.Transparent,
                //contentColor = Color.Magenta,
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.add_button),
                        contentDescription = null,
                        tint = Color.Magenta
                    )
                }
            )
        },
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Second Screen\n" +
                            "Click me to go to First Screen",
                    color = Color.Green,
                    style = androidx.compose.ui.text.TextStyle(textAlign = TextAlign.Center),
                    modifier = Modifier
                        .padding(24.dp)
                        .clickable(onClick = {
                            // this will navigate to second screen
                            navController.navigate("main")
                        })
                )
                val test by viewModel.listFolders.collectAsState()
                FoldersList(test)
            }
        }
    }
}

@Composable
fun FoldersList(test: MutableList<FolderViewModel.Folder>?) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        test?.forEach { Folder ->
            Card(
                modifier = Modifier
                    .padding(all = 10.dp)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(all = 10.dp)) {
                    Text(
                        Folder.title,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.W700,
                        modifier = Modifier.padding(10.dp)
                    )
                    if (Folder.type == FolderViewModel.FolderType.ARTIST) {
                        Icon(Icons.Rounded.Person, contentDescription = null)
                    } else if (Folder.type == FolderViewModel.FolderType.RELEASES) {
                        Icon(Icons.Rounded.ThumbUp, contentDescription = null)
                    }
                }
            }
        }
    }
}


