package ru.byum.muscat.ui.releaseScreen

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
import ru.byum.muscat.data.Release
import ru.byum.muscat.data.network.DiscogsAPI
import ru.byum.muscat.data.network.ReleaseNetwork
import javax.inject.Inject


@HiltViewModel
class ReleaseScreenViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
) : ViewModel() {
    private var _release = MutableStateFlow<Release?>(null)
    var release = _release.asStateFlow()

    private var currentID = 0

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun init(id: Int) {
        if (currentID == id) return

        currentID = id

        viewModelScope.launch(Dispatchers.IO) {
            _loading.update { true }
            val release = musicRepository.getRelease(id)
            _release.update { release }

            _loading.update { false }

        }
    }
}