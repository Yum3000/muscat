package ru.byum.muscat.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.byum.muscat.data.local.database.Music
import ru.byum.muscat.data.local.database.MusicDao
import ru.byum.muscat.data.network.DiscogsAPI
import javax.inject.Inject

interface MusicRepository {
    suspend fun getRelease(id: Int): Release?
    val musics: Flow<List<String>>

    suspend fun add(name: String)
}

class DefaultMusicRepository @Inject constructor(
    private val musicDao: MusicDao,
    private val discogs: DiscogsAPI
) : MusicRepository {
    override suspend fun getRelease(id: Int): Release? {
        return discogs.getRelease(id)?.toRelease()
    }

    override val musics: Flow<List<String>> =
        musicDao.getMusics().map { items -> items.map { it.name } }

    override suspend fun add(name: String) {
        musicDao.insertMusic(Music(name = name))
    }
}
