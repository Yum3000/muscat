package ru.byum.muscat.ui.folderScreen

import androidx.compose.material3.Text
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.byum.muscat.data.MusicRepository
import ru.byum.muscat.data.Release
import javax.inject.Inject


@HiltViewModel
class FolderViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
) : ViewModel() {
    private var _releases = MutableStateFlow(listOf<Release>())
    var releases = _releases.asStateFlow()

    private var _items = MutableStateFlow(listOf<String>())
    var items = _items.asStateFlow()


    private var currentID = 0

    fun init(id: Int) {
        if (currentID == id) return

        currentID = id

        viewModelScope.launch(Dispatchers.IO) {
            musicRepository.addItemToFolder(id, "folder $id item")

            _items.update {
                musicRepository.getFolderItems(id)
            }
        }
    }
}