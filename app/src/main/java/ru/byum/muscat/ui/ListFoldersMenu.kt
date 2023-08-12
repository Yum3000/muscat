package ru.byum.muscat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.byum.muscat.ui.artistScreen.FoldersListMenuViewModel

@ExperimentalMaterial3Api
@Composable
fun ListFoldersMenu(itemID: Int?){

    val foldersViewModel: FoldersListMenuViewModel = hiltViewModel()

    var expanded by remember { mutableStateOf(false) }
    val listOfFolders by foldersViewModel.listOfFolders.collectAsStateWithLifecycle()
    var selectedText by remember { mutableStateOf("Select folder to add") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                modifier = Modifier.background(Color.Gray),
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                listOfFolders.forEach { folder ->
                    DropdownMenuItem(
                        text = { Text(text = folder.title) },
                        onClick = {

                            selectedText = folder.title
                            foldersViewModel.addItemToFolder(folder.id, itemID.toString())

                            expanded = false
                        })
                }
            }
        }
    }
}