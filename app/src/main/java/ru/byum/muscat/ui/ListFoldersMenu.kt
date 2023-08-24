package ru.byum.muscat.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ru.byum.muscat.data.Folder
import ru.byum.muscat.data.FolderType
import ru.byum.muscat.ui.SearchScreen.SearchType

@ExperimentalMaterial3Api
@Composable
fun ListFoldersMenu(
    folderType: FolderType,
    listOfFolders: List<Folder>,
    foldersOfItem: List<Int>,
    onClick: (folderID : Int) -> Unit,
    onClose: () -> Unit,
) {

    //val foldersViewModel: FoldersListMenuViewModel = hiltViewModel()

    //val createModalOpen by foldersViewModel.createModalBottom.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()


    var selectedText by remember { mutableStateOf("Select folder to add") }

    foldersOfItem.forEach { itemIndex ->
        Log.d("out modal", "$itemIndex")
    }


    //if (createModalOpen) {
    ModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        sheetState = sheetState,
        onDismissRequest = { }//foldersViewModel.toggleCreateModalBottom() }
    ) {
        //val chosenFolder by foldersViewModel.chosenFolder.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(onClick = {
                scope
                    .launch {
                        if (sheetState.isVisible) sheetState.hide() else sheetState.expand()
                    }.invokeOnCompletion { onClose() }
            }) {
                Icon(Icons.Default.Close, contentDescription = "close modal bottom sheet")
            }

            listOfFolders.forEach { folder ->

                if (folder.type == folderType) {

                    var selected by remember { mutableStateOf(false) }

                    Log.d("MyMenu", "HELLO THERE ${folder.title}")

                    foldersOfItem.forEach { itemIndex ->
                        Log.d("Menu!", "$itemIndex")
//                    if (folder.id == it) {
//                        Log.d("MENU", "${folder.id} == $it")
//                        selected = true
//                    }
                    }

                    Card(
                        Modifier
                            .padding(5.dp)
                            .fillMaxWidth()
                            .clickable {
                                selected = true
                                onClick(folder.id)
                            }
//                        .clickable(
//                            enabled = (selected == true),
//
//                            onClick = {
//                                onClick(folder.id)
//                                selected = true
////                                scope.launch {
////                                    if (sheetState.isVisible) sheetState.hide() else sheetState.expand()
////                                }.invokeOnCompletion {
////                                    onClose()
////                                }
//                            })
                    ) {
                        //Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.padding(start = 10.dp)) {
                            Row() {
                                Text(
                                    text = folder.title,
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                )

                                Spacer(
                                    Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                )

                                if (selected) {
                                    Icon(Icons.Default.Done, contentDescription = "checked")
                                }
                            }

                        }
                    }
                }
            }
            }
        }
    }