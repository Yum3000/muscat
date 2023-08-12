package ru.byum.muscat.ui.folderScreen

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
class FolderReleasesViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
) : ViewModel() {
    private var _releases = MutableStateFlow(listOf<Release>())
    var releases = _releases.asStateFlow()

    private var currentID = 0

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun init(id: Int) {
        if (currentID == id) return

        currentID = id

        viewModelScope.launch(Dispatchers.IO) {
            _loading.update { true }

            //delay(2000)
            //musicRepository.addItemToFolder(id, "691856")
//            musicRepository.addItemToFolder(id, "372778")
//            musicRepository.addItemToFolder(id, "1097885")
//            musicRepository.addItemToFolder(id, "649306")
            _releases.update {
                musicRepository.getFolderReleases(id)
            }

            _loading.update { false }

        }
    }
}