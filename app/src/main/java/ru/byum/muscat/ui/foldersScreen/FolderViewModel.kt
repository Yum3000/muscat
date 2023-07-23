package ru.byum.muscat.ui.foldersScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.byum.muscat.data.ArtistReleases

class FolderViewModel : ViewModel(){

    enum class FolderType {
        ARTIST, RELEASES
    }

    data class Folder(
        val title: String,
        val type: FolderType
    )

    private val test = mutableListOf(
        Folder("num 1", FolderType.ARTIST),
        Folder("num 2", FolderType.RELEASES),
        Folder("num 3", FolderType.ARTIST),
        Folder("num 4", FolderType.RELEASES),
        Folder("num 5", FolderType.ARTIST),
        Folder("num 6", FolderType.RELEASES),
        Folder("num 7", FolderType.ARTIST),
        Folder("num 8", FolderType.ARTIST)
    )
    private val test2 = mutableListOf(
        Folder("num 1", FolderType.ARTIST),
        Folder("num 2", FolderType.RELEASES),
        Folder("num 3", FolderType.ARTIST),
        Folder("num 4", FolderType.RELEASES),
        Folder("num 5", FolderType.ARTIST),
        Folder("num 6", FolderType.RELEASES),
        Folder("num 7", FolderType.ARTIST),
        Folder("num 8", FolderType.ARTIST),
        Folder("num 9", FolderType.ARTIST)
    )

    private var _listFolders = MutableStateFlow<MutableList<Folder>?>(test)
    var listFolders = _listFolders.asStateFlow()

    fun CreateFolder () {
        _listFolders.update { folders ->
            //folders?.add(Folder("num 9", FolderType.ARTIST))
            test2
        }
    }
}