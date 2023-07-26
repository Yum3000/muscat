package ru.byum.muscat.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Releases(

    @JsonProperty("artist") val artist: String,
    @JsonProperty("id") val id: Int,
    @JsonProperty("thumb") val thumb: String,
    @JsonProperty("title") val title: String,
    @JsonProperty("year") val year: Int,

)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ArtistReleases(
    @JsonProperty("releases") val releases: List<Releases>?,
)