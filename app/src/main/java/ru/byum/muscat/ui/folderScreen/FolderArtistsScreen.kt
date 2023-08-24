package ru.byum.muscat.ui.folderScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
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
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.byum.muscat.ui.Loader

@OptIn(ExperimentalMaterial3Api::class, InternalComposeApi::class)
@Composable
fun FolderArtistsScreen(
    folderId: Int,
    folderTitle: String,
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: FolderArtistsViewModel = hiltViewModel(),
) {
    viewModel.init(folderId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "$folderTitle", fontWeight = FontWeight.SemiBold) },
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
        val artists by viewModel.artists.collectAsState()

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            val loading by viewModel.loading.collectAsState()

            if (loading) {
                Loader()
            }

            Column {
                artists.forEach { artist ->
                    key(artist.id) {
                        Card(
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    artist.name,
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.W700,
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .clickable {
                                            navController.navigate("artistScreen/${artist.id}")
                                        }
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}