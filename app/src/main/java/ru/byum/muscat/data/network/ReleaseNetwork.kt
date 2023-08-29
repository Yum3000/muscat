package ru.byum.muscat.data.network


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ReleaseArtist(
    @JsonProperty("name") val name: String,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Image(
    @JsonProperty("uri") val uri: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Track(
    @JsonProperty("title") val title: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ReleaseNetwork(
    @JsonProperty("id") val id: Int,
    @JsonProperty("title") val title: String,
    @JsonProperty("year") val year: String,
    @JsonProperty("artists") val artists: List<ReleaseArtist>,
    @JsonProperty("images") val images: List<Image>,
    @JsonProperty("tracklist") val tracklist: List<Track>
)
