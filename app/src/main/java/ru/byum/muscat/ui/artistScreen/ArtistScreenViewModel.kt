package ru.byum.muscat.ui.artistScreen

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
class ArtistScreenViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
) : ViewModel() {
    private var _releases = MutableStateFlow<List<Release>>(listOf())
    var releases = _releases.asStateFlow()

    private var _artistID = MutableStateFlow("")
    var artistID = _artistID.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun init(id: String) {
        if (artistID.value != id) {
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