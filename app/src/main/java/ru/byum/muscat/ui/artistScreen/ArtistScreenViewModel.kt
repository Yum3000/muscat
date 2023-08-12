package ru.byum.muscat.ui.artistScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.byum.muscat.data.ArtistReleases
import ru.byum.muscat.data.MusicRepository
import javax.inject.Inject

@HiltViewModel
class ArtistScreenViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
) : ViewModel() {
    private var _listArtistReleases = MutableStateFlow<ArtistReleases?>(null)
    var artistsReleases = _listArtistReleases.asStateFlow()

    private var _artistID = MutableStateFlow("")
    var artistID = _artistID.asStateFlow()

    private var _rating = MutableStateFlow(0)
    var rating = _rating.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun init(id: String) {
        if (artistID.value != id) {
            getRating(id)
            getReleases(id.toInt())

            _artistID.update { id }
        }
    }

    private fun getReleases(id: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.update { true }

            val response = musicRepository.getReleases(id)
            _listArtistReleases.update { response }

            _loading.update { false }
        }
    }

    private fun getRating(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val rating = musicRepository.getRating(id)
            _rating.update { rating }
        }
    }

    fun setRating(id: String, rating: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            musicRepository.setRating(id, rating)
            _rating.update { rating }
        }
    }
}