package ru.byum.muscat.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ArtistsSearchResult(
    @JsonProperty("id") val id: Int?,
    @JsonProperty("title") val title: String?,
    @JsonProperty("cover_image") val cover_image: String?
)


@JsonIgnoreProperties(ignoreUnknown = true)
data class ArtistsSearchResults(
    @JsonProperty("results") val results: List<ArtistsSearchResult>?,
)