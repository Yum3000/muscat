package ru.byum.muscat.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.byum.muscat.data.ArtistReleases
import ru.byum.muscat.data.ArtistsSearchResults
import ru.byum.muscat.data.ReleaseSearchResults

interface RetrofitDiscogsAPI {
    @GET("/releases/{release_id}")
    suspend fun getRelease(
        @Path("release_id") releaseID: Int,
        @Query("token") token: String
    ): Response<ReleaseNetwork>

    @GET("/artists/{artist_id}/releases")
    suspend fun getArtistReleases(@Path("artist_id") artistID: Int?): Response<ArtistReleases>

    @GET("/database/search?")
    suspend fun onSearch(
        @Query("query") query: String,
        @Query("type") type: String,
        @Query("token") token: String
    ): Response<ReleaseSearchResults>

    @GET("/database/search?")
    suspend fun onSearchArtists(
        @Query("query") query: String,
        @Query("type") type: String,
        @Query("token") token: String
    ): Response<ArtistsSearchResults>
}