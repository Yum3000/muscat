package ru.byum.muscat.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import kotlinx.coroutines.launch
import ru.byum.muscat.data.Folder

@ExperimentalMaterial3Api
@Composable
fun ListFoldersMenu(
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
                Card(
                    Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
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
                            Text(text = folder.title)

                            var selected by remember { mutableStateOf(false) }

                            Checkbox(

                                checked = selected,
                                onCheckedChange = {
                                    selected = it
                                    onClick(folder.id)

                                }
                            )
                        }
                        }
                }
            }
        }
    }