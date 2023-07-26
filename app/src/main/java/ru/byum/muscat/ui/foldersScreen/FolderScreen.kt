package ru.byum.muscat.ui.foldersScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class, InternalComposeApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FolderScreen(
    folderID: String,
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: FolderScreenViewModel = hiltViewModel(),

) {
//    val folders by viewModel.folders.collectAsStateWithLifecycle()
//    val typeFolder by viewModel.typeFolder.collectAsState()
//    var showMenu by remember { mutableStateOf(false) }


    Scaffold(

        topBar = {
            TopAppBar(title = {}, navigationIcon = {
                IconButton(modifier = Modifier.width(96.dp),
                    onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        modifier = Modifier.size(128.dp),
                        contentDescription = "Back"
                    )
                }
            },
                actions = {
                })
        },

    ) { padding ->

        Button(onClick = { viewModel.addContentIntoFolder(folderID.toInt(), "MEW ARTIST")

        }) {
            Text(text = "Create new content")
        }

        //Text()


    }








}


