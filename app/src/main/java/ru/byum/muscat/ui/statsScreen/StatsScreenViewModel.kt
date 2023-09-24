package ru.byum.muscat.ui.statsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.byum.muscat.data.MusicRepository
import javax.inject.Inject

data class Stats(
    val foldersCount: Int = 0,
    val artistFoldersCount: Int = 0,
    val releaseFoldersCount: Int = 0,
    val itemsInFoldersCount: Int = 0,
    val biggestFolder: String = "",
    val biggestFolderItemsCount: Int = 0,
    val smallestFolder: String = "",
    val smallestFolderItemsCount: Int = 0,
    val ratingsCounts: IntArray = intArrayOf(),
    val mostRatedGenres: List<String> = listOf(),
)

@HiltViewModel
class StatsScreenViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
) : ViewModel() {
    private var _data = MutableStateFlow(Stats())
    var data = _data.asStateFlow()

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            val folderStat = musicRepository.getFoldersStats()
            val ratingStat = musicRepository.getRatingStats()
            val topGenres = ratingStat.genresStat.keys.toMutableList()
            topGenres.sortWith { a, b -> ratingStat.genresStat[a]!! - ratingStat.genresStat[b]!! }
            val stat = Stats(
                folderStat.count,
                folderStat.artistCount,
                folderStat.releaseCount,
                folderStat.itemsCount,
                folderStat.biggest.first,
                folderStat.biggest.second,
                folderStat.smallest.first,
                folderStat.smallest.second,
                ratingStat.counts,
                topGenres,
            )

            _data.update { stat }
        }
    }
}