package ru.byum.muscat.ui.RatingBar

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
import ru.byum.muscat.data.MusicRepository
import javax.inject.Inject

@HiltViewModel
class RatingBarViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
) : ViewModel() {

    private var _rating = MutableStateFlow(0)
    var rating = _rating.asStateFlow()

    var currentID: Int = 0

    fun init(id: Int) {
        if (currentID == id) return
        currentID = id
        getRating(id)
    }

    fun getRating(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val rating = musicRepository.getRating(id)
            _rating.update { rating }
        }
    }

    fun setRating(id: Int, rating: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            musicRepository.setRating(id, rating)
            _rating.update { rating }
        }
    }
}