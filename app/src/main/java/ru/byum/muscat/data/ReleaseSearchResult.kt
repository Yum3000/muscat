package ru.byum.muscat.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ReleaseSearchResult(
    @JsonProperty("id") val id: Int?,
    @JsonProperty("year") val year: String?,
    @JsonProperty("title") val title: String?,
    @JsonProperty("thumb") val thumb: String?,
    @JsonProperty("cover_image") val cover_image: String?
)


@JsonIgnoreProperties(ignoreUnknown = true)
data class ReleaseSearchResults(
    @JsonProperty("results") val results: List<ReleaseSearchResult>?,
)

