package ru.byum.muscat.ui.artistScreen

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
import ru.byum.muscat.data.Artist
import ru.byum.muscat.data.FoldersItems
import ru.byum.muscat.data.MusicRepository
import ru.byum.muscat.data.Release
import javax.inject.Inject

@HiltViewModel
class ArtistScreenViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
) : ViewModel() {
    private var _releases = MutableStateFlow<List<Release>>(listOf())
    var releases = _releases.asStateFlow()

    private var _artistID = MutableStateFlow("")
    var artistID = _artistID.asStateFlow()

    private var _artist = MutableStateFlow<Artist?>(null)
    var artist = _artist.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private var _itemInFolders = MutableStateFlow<List<Int>>(listOf())
    var itemInFolders = _itemInFolders.asStateFlow()


    fun init(id: String) {
        if (artistID.value != id) {

            viewModelScope.launch(Dispatchers.IO) {
                val artist = musicRepository.getArtist(id.toInt())
                _artist.update { artist }
            }

            getReleases(id.toInt())
            _artistID.update { id }
        }
    }

    private fun getReleases(id: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.update { true }

            val releases = musicRepository.getArtistReleases(id)
            _releases.update { releases }

            _loading.update { false }
        }
    }

    fun setArtistRating(artistID: Int, rating: Int){
        val artist = _artist.value
        artist?.rating = rating
        _artist.update { artist }
        viewModelScope.launch(Dispatchers.IO) {
            musicRepository.setRating(artistID, rating)
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


    private var _currentReleaseAddToFolder = MutableStateFlow<Int>(0)
    var currentReleaseAddToFolder = _currentReleaseAddToFolder.asStateFlow()

    private var _chosenFolder = MutableStateFlow(false)
    var chosenFolder = _chosenFolder.asStateFlow()

    private var _createModalBottom = MutableStateFlow(false)
    var createModalBottom = _createModalBottom.asStateFlow()

    fun toggleCreateModalBottom() {
        val state = createModalBottom.value
        _createModalBottom.update { !state }
    }


    var listOfFolders = musicRepository.folders.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        listOf()
    )

    fun addItemToFolder(folderID: Int) {
        val itemID = currentReleaseAddToFolder.value.toString()

        viewModelScope.launch(Dispatchers.IO){
            musicRepository.addItemToFolder(folderID, itemID)
        }
    }

    fun setAddToFolderRelease(id: Int) {
        _currentReleaseAddToFolder.update { id }
    }

    fun checkItemInFolders(itemID: Int) {

        _loading.update { true }
        viewModelScope.launch(Dispatchers.IO) {
            _itemInFolders.update { musicRepository.getFoldersWithItem(itemID) }
        }
        _loading.update { false }
    }

}