package ru.byum.muscat.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.byum.muscat.data.MusicRepository
import ru.byum.muscat.data.DefaultMusicRepository
import ru.byum.muscat.data.network.DefaultDiscogsAPI
import ru.byum.muscat.data.network.DiscogsAPI
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsMusicRepository(
        musicRepository: DefaultMusicRepository
    ): MusicRepository

    @Singleton
    @Binds
    fun bindsDiscogsAPI(
        discogsAPI: DefaultDiscogsAPI
    ): DiscogsAPI
}
