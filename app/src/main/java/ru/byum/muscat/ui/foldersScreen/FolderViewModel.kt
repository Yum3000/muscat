package ru.byum.muscat.ui.foldersScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.byum.muscat.data.FolderType
import ru.byum.muscat.data.MusicRepository
import javax.inject.Inject



@HiltViewModel
class FolderViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
) : ViewModel() {
    var folders = musicRepository.folders.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        listOf()
    )

    private var _createFolderType = MutableStateFlow(FolderType.RELEASES)
    var createFolderType = _createFolderType.asStateFlow()

    private var _createModalBottom = MutableStateFlow(false)
    var createModalBottom = _createModalBottom.asStateFlow()

    private val _displayedFoldersType = MutableStateFlow<FolderType?>(FolderType.RELEASES)
    var displayedFoldersType = _displayedFoldersType.asStateFlow()



    fun deleteFolder(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            musicRepository.deleteFolder(id)
        }
    }

    fun setTypeFolderArtist() {
        _createFolderType.update { FolderType.ARTIST }
    }

    fun setTypeFolderReleases() {
        _createFolderType.update { FolderType.RELEASES }
    }

    fun toggleCreateModalBottom () {
        val state = createModalBottom.value
        _createModalBottom.update{!state}
    }

    fun changeDisplayedType(folderType: FolderType?) {
        _displayedFoldersType.update { folderType }
    }


    fun createFolder(title: String, type: FolderType) {
        viewModelScope.launch(Dispatchers.IO) {
            musicRepository.createFolder(title, type)
        }
    }
}