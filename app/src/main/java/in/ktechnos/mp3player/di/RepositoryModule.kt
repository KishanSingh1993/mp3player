package `in`.ktechnos.mp3player.di

import `in`.ktechnos.mp3player.data.repository.HeadphonePlayerRepositoryImpl
import `in`.ktechnos.mp3player.domain.repository.HeadphonePlayerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    @Singleton
    abstract fun bindHeadphonePlayerRepository(
        repository: HeadphonePlayerRepositoryImpl
    ): HeadphonePlayerRepository


}