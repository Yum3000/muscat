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
import ru.byum.muscat.data.ArtistsSearchResults
import ru.byum.muscat.data.MusicRepository
import ru.byum.muscat.data.ReleaseSearchResults
import ru.byum.muscat.ui.musicScreen.SearchType
import javax.inject.Inject

@HiltViewModel
class ArtistScreenViewModel @Inject constructor(
    private val musicRepository: MusicRepository
) : ViewModel() {

        private var _listArtistReleases = MutableStateFlow<ArtistReleases?>(null)
        var artistsReleases = _listArtistReleases.asStateFlow()


        fun getReleases(id: Int?) {
            viewModelScope.launch(Dispatchers.IO){
                val response = musicRepository.getReleases(id)
                _listArtistReleases.update {response}
            }
        }
}