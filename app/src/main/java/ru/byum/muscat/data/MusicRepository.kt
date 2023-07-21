package ru.byum.muscat.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Call
import ru.byum.muscat.data.local.database.Music
import ru.byum.muscat.data.local.database.MusicDao
import ru.byum.muscat.data.network.DiscogsAPI
import ru.byum.muscat.data.network.RetrofitInstance
import javax.inject.Inject

interface MusicRepository {
    suspend fun getRelease(id: Int): Release?
    suspend fun onSearch(query:String) : ReleaseSearchResults?
    suspend fun onSearchArtists(query:String) : ArtistsSearchResults?
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

    override suspend fun onSearch(query: String): ReleaseSearchResults? {
        return discogs.onSearch(query)
    }

    override suspend fun onSearchArtists(query: String): ArtistsSearchResults? {
        return discogs.onSearchArtists(query)
    }

    override val musics: Flow<List<String>> =
        musicDao.getMusics().map { items -> items.map { it.name } }

    override suspend fun add(name: String) {
        musicDao.insertMusic(Music(name = name))
    }
}
