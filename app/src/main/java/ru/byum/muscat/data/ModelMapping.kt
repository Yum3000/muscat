package ru.byum.muscat.data

import ru.byum.muscat.data.network.ArtistNetwork
import ru.byum.muscat.data.network.ReleaseNetwork

fun ReleaseNetwork.toRelease(): Release {
    val genre = genres.getOrElse(0) { "" }
    val tracks = tracklist.map { it.title }
    val image = images[0].uri ?: ""

    return Release(id, title, year, artists[0].name, image, tracks, genre)
}

fun ArtistNetwork.toArtist(): Artist {
    return Artist(id, name, images[0].uri ?: "")
}

fun ArtistRelease.toRelease(): Release {
    return Release(id, title, year.toString(), artist, thumb, tracklist = listOf())
}

