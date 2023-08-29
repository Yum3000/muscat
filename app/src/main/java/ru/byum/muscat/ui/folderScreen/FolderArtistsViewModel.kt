package ru.byum.muscat.ui.folderScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.byum.muscat.data.Artist
import ru.byum.muscat.data.MusicRepository
import javax.inject.Inject

@HiltViewModel
class FolderArtistsViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
) : ViewModel() {
    private var _artists = MutableStateFlow(listOf<Artist>())
    var artists = _artists.asStateFlow()

    private var currentID = 0

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun init(id: Int) {
        if (currentID == id) return

        currentID = id

        viewModelScope.launch(Dispatchers.IO) {
            _loading.update { true }

            _artists.update {
                musicRepository.getFolderArtists(id)
            }

            _loading.update { false }

        }
    }

    fun setArtistRating(artistID: Int, rating: Int) {
        val artists = _artists.value.toMutableList()
        val artist = artists.find { it.id == artistID }
        artist?.rating = rating

        _artists.update { artists }

        viewModelScope.launch(Dispatchers.IO) {
            musicRepository.setRating(artistID, rating)
        }
    }

    fun deleteArtist(folder: Int, artistID: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            musicRepository.removeItemFromFolder(folder, artistID)
            _artists.update { musicRepository.getFolderArtists(folder)  }
        }
    }
}