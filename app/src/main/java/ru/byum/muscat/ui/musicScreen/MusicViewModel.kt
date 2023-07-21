package ru.byum.muscat.ui.musicScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.byum.muscat.data.ArtistsSearchResults
import ru.byum.muscat.data.MusicRepository
import ru.byum.muscat.data.ReleaseSearchResult
import ru.byum.muscat.data.ReleaseSearchResults
import javax.inject.Inject

public const val TAG = "Music View Model"

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val musicRepository: MusicRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(MusicUiState())
    val uiState: StateFlow<MusicUiState> = _uiState.asStateFlow()


    private val _searchString = MutableStateFlow("")
    val searchString = _searchString.asStateFlow()

    private val _movies = MutableStateFlow<List<ReleaseSearchResult>>(
        emptyList()
    )
    val movies = _movies.asStateFlow()

    var listCurrentResults: ReleaseSearchResults? = null
    var listCurrentArtists: ArtistsSearchResults? = null

    fun onSearchStringChange(searchString: String){
        _searchString.value = searchString
    }

    fun getRelease() {
        viewModelScope.launch {
            val release  = musicRepository.getRelease(23579)
            if (release != null) {
                _uiState.update {
                    it.copy(
                        title = release.title,
                        year = release.year,
                    )
                }
            }
        }
    }

    fun onSearch(searchString: String) {

        viewModelScope.launch(Dispatchers.IO){
            //_uiState.value = Status.Loading(null)

            val response = musicRepository.onSearch(searchString)
            listCurrentResults = response

            val response2 = musicRepository.onSearchArtists(searchString)
            listCurrentArtists = response2

            Log.d(TAG, "test VIEW MODEL {$response}")
            if (response != null) {

                Log.d(TAG, "THIS!!!! ${response.results?.get(1)}")

                _uiState.update {
                    it.copy(
                        title = searchString,
                        year = searchString,
                    )
                }
            }
        }
    }

    fun onSearchArtists(searchString: String) {

        viewModelScope.launch(Dispatchers.IO){
            //_uiState.value = Status.Loading(null)

            val response2 = musicRepository.onSearchArtists(searchString)
            listCurrentArtists = response2

            Log.d(TAG, "test VIEW MODEL {$response2}")
            if (response2 != null) {

                Log.d(TAG, "THIS!!!! ${response2.results?.get(1)}")

                _uiState.update {
                    it.copy(
                        title = searchString,
                        year = searchString,
                    )
                }
            }
        }
    }
}

data class MusicUiState(
    val title: String = "---",
    val year: String = "---",
)

