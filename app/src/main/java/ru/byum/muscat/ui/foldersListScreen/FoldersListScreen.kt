package ru.byum.muscat.ui.foldersListScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.byum.muscat.R
import ru.byum.muscat.data.Folder
import ru.byum.muscat.data.FolderType

@OptIn(ExperimentalMaterial3Api::class, InternalComposeApi::class)
@Composable
fun FoldersListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: FoldersListViewModel = hiltViewModel(),
) {
    var showMenu by remember { mutableStateOf(false) }
    val displayedType by viewModel.displayedFoldersType.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
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
                actions = {
                    IconButton(
                        onClick = { showMenu = !showMenu }
                    ) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "menu"
                        )
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "All") },
                            onClick = { viewModel.changeDisplayedType(null) },
                            trailingIcon = {
                                if (displayedType == null) {
                                    Icon(Icons.Default.Done, contentDescription = "selected")
                                }
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Artist") },
                            onClick = { viewModel.changeDisplayedType(FolderType.ARTIST) },
                            trailingIcon = {
                                if (displayedType == FolderType.ARTIST) {
                                    Icon(Icons.Default.Done, contentDescription = "selected")
                                }
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Release") },
                            onClick = { viewModel.changeDisplayedType(FolderType.RELEASES) },
                            trailingIcon = {
                                if (displayedType == FolderType.RELEASES) {
                                    Icon(Icons.Default.Done, contentDescription = "selected")
                                }
                            }
                        )
                    }
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.toggleCreateModalBottom() },
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.add_button),
                        contentDescription = null,
                        tint = Color.Magenta
                    )
                }
            )
        },

        floatingActionButtonPosition = FabPosition.End,
    ) { padding ->
        val folders by viewModel.folders.collectAsStateWithLifecycle()

        Box(modifier = Modifier.padding(padding)) {
            FoldersList(
                folders,
                { id -> viewModel.deleteFolder(id) },
                navController,
                displayedType
            )
        }

        val createModalOpen by viewModel.createModalBottom.collectAsState()
        val sheetState = rememberModalBottomSheetState()
        val scope = rememberCoroutineScope()

        if (createModalOpen) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxWidth(),
                sheetState = sheetState,
                onDismissRequest = { viewModel.toggleCreateModalBottom() }
            ) {
                val message = remember { mutableStateOf("") }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = message.value,
                        textStyle = TextStyle(fontSize = 20.sp),
                        placeholder = { Text(text = "Enter title of a new folder") },
                        onValueChange = { newText -> message.value = newText },
                        singleLine = true,
                    )

                    Text(text = "Select type for a new folder")

                    val createFolderType by viewModel.createFolderType.collectAsState()
                    Row {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = createFolderType == FolderType.ARTIST,
                                onClick = {
                                    viewModel.setTypeFolderArtist()
                                }
                            )
                            Text(text = "Artists")
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = createFolderType == FolderType.RELEASES,
                                onClick = {
                                    viewModel.setTypeFolderReleases()
                                }
                            )
                            Text(text = "Releases")
                        }
                    }

                    Button(
                        enabled = message.value.isNotEmpty(),
                        onClick = {
                            viewModel.createFolder(message.value, createFolderType)

                            scope.launch {
                                if (sheetState.isVisible) sheetState.hide() else sheetState.expand()
                            }.invokeOnCompletion {
                                viewModel.toggleCreateModalBottom()
                            }
                        }

                    ) {
                        Text(text = "Create new folder")
                    }
                }
            }
        }
    }
}

@Composable
fun FoldersList(
    folders: List<Folder>,
    onDelete: (id: Int) -> Unit,
    navController: NavController,
    displayedType: FolderType?
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        folders.forEach { folder ->
            if (displayedType == null || folder.type == displayedType) {
                key(folder.id) {
                    Card(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.padding(start = 10.dp)) {
                                when (folder.type) {
                                    FolderType.ARTIST -> Icon(Icons.Rounded.Person, null)
                                    FolderType.RELEASES -> Icon(Icons.Rounded.ThumbUp, null)
                                }
                            }
                            Text(
                                folder.title,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.W700,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clickable {
                                        navController.navigate("folder/${folder.id}")
                                    }
                            )

                            Spacer(
                                Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                            )

                            IconButton(
                                onClick = { onDelete(folder.id) }) {
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
}
