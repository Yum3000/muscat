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

            _releases.update { musicRepository.getFolderReleases(id) }

            _loading.update { false }

        }
    }

    fun setReleaseRating(releaseID: Int, rating: Int) {
        val releases = _releases.value.toMutableList()
        val release = releases.find { it.id == releaseID }
        release?.rating = rating

        _releases.update { releases }

        viewModelScope.launch(Dispatchers.IO) {
            musicRepository.setRating(releaseID, rating)
        }
    }
}