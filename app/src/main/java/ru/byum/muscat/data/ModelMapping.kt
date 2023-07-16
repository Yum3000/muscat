package ru.byum.muscat.data

import ru.byum.muscat.data.network.ReleaseNetwork

fun ReleaseNetwork.toRelease(): Release {
    return Release(id, title, released)
}

fun Release.toNetworkRelease(): ReleaseNetwork {
    return ReleaseNetwork(id, title, released)
}