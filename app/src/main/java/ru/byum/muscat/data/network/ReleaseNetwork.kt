package ru.byum.muscat.data.network

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ReleaseNetwork(
    @JsonProperty("id") val id: Int,
    @JsonProperty("title") val title: String,
    @JsonProperty("released") val released: String,
)
