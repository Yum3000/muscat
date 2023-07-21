package ru.byum.muscat.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class TrackSearchResult(
    @JsonProperty("id") val id: Int?,
    @JsonProperty("year") val year: String?,
    @JsonProperty("title") val title: String?,
    @JsonProperty("cover_image") val cover_image: String?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TrackSearchResults(
    @JsonProperty("results") val results: List<TrackSearchResult>?,
)