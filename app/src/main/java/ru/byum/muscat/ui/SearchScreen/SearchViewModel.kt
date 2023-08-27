package ru.byum.muscat.ui.SearchScreen

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
import ru.byum.muscat.data.network.NetworkArtistsSearchResults
import ru.byum.muscat.data.MusicRepository
import ru.byum.muscat.data.network.NetworkReleaseSearchResults
import javax.inject.Inject

enum class SearchType {
    ARTIST, RELEASE
}

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val musicRepository: MusicRepository
) : ViewModel() {
    private val _searchType = MutableStateFlow(SearchType.RELEASE)
    val searchType = _searchType.asStateFlow()

    private var _listCurrentResults = MutableStateFlow<NetworkReleaseSearchResults?>(null)
    var releasesSearchResult = _listCurrentResults.asStateFlow()

    private var _listCurrentArtists = MutableStateFlow<NetworkArtistsSearchResults?>(null)
    var artistsSearchResult = _listCurrentArtists.asStateFlow()

    private var _currentArtistAddToFolder = MutableStateFlow<Int?>(0)
    var currentArtistAddToFolder = _currentArtistAddToFolder.asStateFlow()

    private var _itemInFolders = MutableStateFlow<List<Int>>(listOf())
    var itemInFolders = _itemInFolders.asStateFlow()

    private var _currentReleaseAddToFolder = MutableStateFlow<Int?>(0)
    var currentReleaseAddToFolder = _currentReleaseAddToFolder.asStateFlow()

    var listOfFolders = musicRepository.folders.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        listOf()
    )

    fun addArtistToFolder(folderID: Int) {
        val itemID = currentArtistAddToFolder.value.toString()

        viewModelScope.launch(Dispatchers.IO){
            musicRepository.addItemToFolder(folderID, itemID)
        }
    }

    fun addReleaseToFolder(folderID: Int) {
        val itemID = currentReleaseAddToFolder.value.toString()

        viewModelScope.launch(Dispatchers.IO){
            musicRepository.addItemToFolder(folderID, itemID)
        }
    }

    fun setAddToFolderArtist(id: Int?) {
        _currentArtistAddToFolder.update { id }
    }

    fun setAddToFolderRelease(id: Int?) {
        _currentReleaseAddToFolder.update { id }
    }

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun setSearchArtist() {
        _searchType.update { SearchType.ARTIST }
    }

    fun setSearchRelease() {
        _searchType.update { SearchType.RELEASE }
    }

    fun searchReleases(searchString: String) {
        viewModelScope.launch(Dispatchers.IO){
            _loading.update { true }

            val response = musicRepository.searchReleases(searchString)
            _listCurrentResults.update {response}

            _loading.update { false }
        }
    }

    fun searchArtists(searchString: String) {
        viewModelScope.launch(Dispatchers.IO){
            _loading.update { true }

            val response = musicRepository.searchArtists(searchString)
            _listCurrentArtists.update { response }

            _loading.update { false }
        }
    }
}

