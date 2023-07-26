package ru.byum.muscat.ui.foldersScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.byum.muscat.data.FolderType
import ru.byum.muscat.data.MusicRepository
import javax.inject.Inject


@HiltViewModel
class FolderScreenViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
) : ViewModel() {



    fun addContentIntoFolder(idFolder: Int, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            musicRepository.addContentIntoFolder(idFolder, content)
        }
    }

}
