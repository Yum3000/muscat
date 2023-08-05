package ru.byum.muscat.ui.folderScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class, InternalComposeApi::class)
@Composable
fun FolderScreen(
    folderId: Int,
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: FolderViewModel = hiltViewModel(),
) {
    viewModel.init(folderId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "id is: $folderId") },
                navigationIcon = {
                    IconButton(modifier = Modifier.width(96.dp),
                        onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            modifier = Modifier.size(128.dp),
                            contentDescription = "Back"
                        )
                    }
                },
                actions = { }
            )
        },
    ) { padding ->
        val items by viewModel.items.collectAsState()
        Column(modifier = Modifier.padding(padding).fillMaxWidth()) {
            items.forEach({ item -> Text(text = item) })
        }
    }
}


