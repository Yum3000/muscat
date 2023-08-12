package ru.byum.muscat.ui.artistScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.byum.muscat.data.MusicRepository
import javax.inject.Inject

@HiltViewModel
class FoldersListMenuViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
) : ViewModel() {


    var listOfFolders = musicRepository.folders.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        listOf()
    )

    fun addItemToFolder(folderID: Int, itemID: String) {
        viewModelScope.launch(Dispatchers.IO){
            musicRepository.addItemToFolder(folderID, itemID)
        }
    }
}
