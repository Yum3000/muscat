package ru.byum.muscat.ui.folderScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.byum.muscat.data.FolderType
import ru.byum.muscat.data.MusicRepository
import javax.inject.Inject


@HiltViewModel
class FolderViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
) : ViewModel() {
    fun deleteFolder(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            musicRepository.deleteFolder(id)
        }
    }
}