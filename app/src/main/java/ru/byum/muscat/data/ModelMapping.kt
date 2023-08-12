package ru.byum.muscat.data

import ru.byum.muscat.data.network.ArtistNetwork
import ru.byum.muscat.data.network.ReleaseArtist
import ru.byum.muscat.data.network.ReleaseNetwork

fun ReleaseNetwork.toRelease(): Release {
    return Release(id, title, year, artists[0].name)
}

fun Release.toNetworkRelease(): ReleaseNetwork {
    return ReleaseNetwork(id, title, year, listOf(ReleaseArtist(artist)))
}

fun ArtistNetwork.toArtist(): Artist {
    return Artist(id, name)
}

