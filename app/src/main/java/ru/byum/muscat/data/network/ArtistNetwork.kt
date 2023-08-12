package ru.byum.muscat.data.network

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ArtistNetwork(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
)