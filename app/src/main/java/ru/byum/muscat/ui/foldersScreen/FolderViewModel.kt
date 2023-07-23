package ru.byum.muscat.ui.foldersScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
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

    fun deleteFolder(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            musicRepository.deleteFolder(id)
        }
    }

    fun createFolder(title: String, type: FolderType) {
        viewModelScope.launch(Dispatchers.IO) {
            musicRepository.createFolder(title, type)
        }
    }
}