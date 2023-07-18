package ru.byum.muscat.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.byum.muscat.data.MusicRepository
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val musicRepository: MusicRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(MusicUiState())
    val uiState: StateFlow<MusicUiState> = _uiState.asStateFlow()

    fun getRelease() {
        viewModelScope.launch {
            val release  = musicRepository.getRelease("249504")
            if (release != null) {
                _uiState.update {
                    it.copy(
                        title = release.title,
                        released = release.released,
                    )
                }
            }
        }
    }
}

data class MusicUiState(
    val title: String = "---",
    val released: String = "---",
)
