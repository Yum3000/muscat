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

    private var _releasesRatings = MutableStateFlow(mapOf<Int, Int>())
    var releasesRatings = _releasesRatings.asStateFlow()

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

            val releases = musicRepository.getReleases(id)
            _listArtistReleases.update { releases }

            val releasesIds = releases?.releases?.map { it.id }

            if (!releasesIds.isNullOrEmpty()) {
                val ratings = musicRepository.getRatings(releasesIds)
                val old = _releasesRatings.value.toMutableMap()
                ratings.forEach { old[it.itemId] = it.rating  }
                _releasesRatings.update { old }
            }

            _loading.update { false }
        }
    }

    fun setRating(itemID: Int, rating: Int) {
        val ratings = _releasesRatings.value.toMutableMap()
        ratings[itemID] = rating
        _releasesRatings.update { ratings }

        viewModelScope.launch(Dispatchers.IO) {
            musicRepository.setRating(itemID, rating)
        }
    }
}