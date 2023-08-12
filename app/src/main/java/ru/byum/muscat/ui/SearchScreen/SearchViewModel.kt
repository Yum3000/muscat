package ru.byum.muscat.ui.SearchScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.byum.muscat.data.ArtistsSearchResults
import ru.byum.muscat.data.MusicRepository
import ru.byum.muscat.data.ReleaseSearchResults
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

    private var _listCurrentResults = MutableStateFlow<ReleaseSearchResults?>(null)
    var releasesSearchResult = _listCurrentResults.asStateFlow()

    private var _listCurrentArtists = MutableStateFlow<ArtistsSearchResults?>(null)
    var artistsSearchResult = _listCurrentArtists.asStateFlow()

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

