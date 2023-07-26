package ru.byum.muscat.ui.foldersScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import ru.byum.muscat.R
import ru.byum.muscat.data.Folder
import ru.byum.muscat.data.FolderType
import javax.annotation.Nullable


@OptIn(ExperimentalMaterial3Api::class, InternalComposeApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FoldersListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: FolderViewModel = hiltViewModel(),
) {
    val folders by viewModel.folders.collectAsStateWithLifecycle()
    val typeFolder by viewModel.createFolderType.collectAsState()
    var showMenu by remember { mutableStateOf(false) }
    val createModalOpen by viewModel.createModalBottom.collectAsState()

    val displayedType by viewModel.displayedFoldersType.collectAsState()


    //val state = rememberModalBottomSheetState()

    if (createModalOpen) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.toggleCreateModalBottom()},
        ) {

            val message = remember { mutableStateOf("") }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Row(modifier = Modifier.padding(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextField(
                        value = message.value,
                        //modifier = Modifier.padding()
                        textStyle = TextStyle(fontSize = 25.sp),
                        placeholder = { Text(text = "Enter title of a new folder") },
                        onValueChange = { newText -> message.value = newText },
                        singleLine = true,
                        //shape = MaterialTheme.shapes.small
                    )
                }

                Text(text = "Select type for a new folder")

                val selectedFolderType by viewModel.createFolderType.collectAsState()

                Row(modifier = Modifier.padding(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    RadioButton(selected = selectedFolderType == FolderType.ARTIST, onClick = {
                        viewModel.setTypeFolderArtist()})
                    Text(text = "Artists")
                }

                Row(modifier = Modifier.padding(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    RadioButton(selected = selectedFolderType == FolderType.RELEASES, onClick = {
                        viewModel.setTypeFolderReleases()
                    })
                    Text(text = "Releases")
                }

                Button(onClick = { viewModel.createFolder(message.value, viewModel.createFolderType.value)
                    viewModel.toggleCreateModalBottom()
                    }) {
                    Text(text = "Create new folder")
                }
            }
        }
    }

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
            })
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
                })
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {

            FoldersList(folders, { id -> viewModel.deleteFolder(id) }, navController,
                displayedType)
        }
    }
}

@Composable
fun FoldersList(test: List<Folder>, onDelete: (id: Int) -> Unit, navController: NavController,
                displayedType:FolderType?
                ) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

        test.forEach { folder ->
            if (folder.type == displayedType) {
                key(folder.id) {
                    Card(
                        modifier = Modifier
                            .padding(all = 5.dp)
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding()
                        ) {

                            when (folder.type) {
                                FolderType.ARTIST -> Icon(Icons.Rounded.Person, null)
                                FolderType.RELEASES -> Icon(Icons.Rounded.ThumbUp, null)
                            }
                            Text(
                                folder.title,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.W700,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clickable { navController.navigate("FolderScreen") }
                            )

                            Spacer(
                                Modifier
                                    .weight(1f)
                                    .fillMaxHeight())

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
