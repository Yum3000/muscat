package ru.byum.muscat.ui.foldersScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Delete
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
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ru.byum.muscat.R
import ru.byum.muscat.data.Folder
import ru.byum.muscat.data.FolderType


@OptIn(ExperimentalMaterial3Api::class, InternalComposeApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FoldersScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: FolderViewModel = hiltViewModel(),
) {
    val folders by viewModel.folders.collectAsStateWithLifecycle()

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
            })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.createFolder("folder ${folders.size}", FolderType.RELEASES) },
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.add_button),
                        contentDescription = null,
                        tint = Color.Magenta
                    )
                })
        },
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {

            FoldersList(folders, { id -> viewModel.deleteFolder(id) })
        }
    }
}

@Composable
fun FoldersList(test: List<Folder>, onDelete: (id: Int) -> Unit) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        test.forEach { folder ->
            key(folder.id) {
                Card(
                    modifier = Modifier
                        .padding(all = 10.dp)
                        .fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(all = 10.dp)) {
                        Text(
                            folder.title,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.W700,
                            modifier = Modifier.padding(10.dp)
                        )

                        when (folder.type) {
                            FolderType.ARTIST -> Icon(Icons.Rounded.Person, null)
                            FolderType.RELEASES -> Icon(Icons.Rounded.ThumbUp, null)
                        }
                        IconButton(onClick = { onDelete(folder.id) }) {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "delete",
                            )
                        }
                    }
                }
            }
        }
    }
}


