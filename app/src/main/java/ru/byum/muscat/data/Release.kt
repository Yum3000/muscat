package ru.byum.muscat.data

data class Release(
    val id: Int = 0,
    val title: String = "",
    val year: String = "",
    val artist: String = "",
    val image: String = "",
    var rating: Int = 0,
)
