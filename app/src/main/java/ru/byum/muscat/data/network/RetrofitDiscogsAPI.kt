package ru.byum.muscat.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.byum.muscat.data.ArtistReleases

interface RetrofitDiscogsAPI {
    @GET("/masters/{master_id}")
    suspend fun getRelease(
        @Path("master_id") releaseID: Int,
        @Query("token") token: String
    ): Response<ReleaseNetwork>

    @GET("/artists/{artist_id}")
    suspend fun getArtist(
        @Path("artist_id") artistID: Int,
        @Query("token") token: String
    ): Response<ArtistNetwork>

    @GET("/artists/{artist_id}/releases")
    suspend fun getArtistReleases(@Path("artist_id") artistID: Int?): Response<ArtistReleases>

    @GET("/database/search?")
    suspend fun onSearch(
        @Query("query") query: String,
        @Query("type") type: String,
        @Query("token") token: String
    ): Response<NetworkReleaseSearchResults>

    @GET("/database/search?")
    suspend fun onSearchArtists(
        @Query("query") query: String,
        @Query("type") type: String,
        @Query("token") token: String
    ): Response<NetworkArtistsSearchResults>
}