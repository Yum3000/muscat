package ru.byum.muscat.data.network

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class NetworkArtistsSearchResult(
    @JsonProperty("id") val id: Int?,
    @JsonProperty("title") val title: String?,
    @JsonProperty("thumb") val thumb: String?,
    @JsonProperty("cover_image") val cover_image: String?
)


@JsonIgnoreProperties(ignoreUnknown = true)
data class NetworkArtistsSearchResults(
    @JsonProperty("results") val results: List<NetworkArtistsSearchResult>?,
)