package `in`.ktechnos.mp3player.data.repository

import `in`.ktechnos.mp3player.domain.model.AudioMetadata
import `in`.ktechnos.mp3player.domain.repository.HeadphonePlayerRepository
import `in`.ktechnos.mp3player.util.audio.MetadataHelper
import `in`.ktechnos.mp3player.util.audio.UserPreferences
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HeadphonePlayerRepositoryImpl @Inject constructor(
    private val metadataHelper: MetadataHelper,
    private val userPreferences: UserPreferences
) : HeadphonePlayerRepository {

    override suspend fun loadCoverBitmap(context: Context, uri: Uri): Bitmap? {
        return withContext(Dispatchers.IO) {
            metadataHelper.getAlbumArt(context = context, uri = uri)
        }
    }

    override suspend fun getAudios(): List<AudioMetadata> {
        return withContext(Dispatchers.IO) {
            metadataHelper.getAudios()
        }
    }

    override suspend fun likeOrNotSong(id: Long) {
        withContext(Dispatchers.IO){
            userPreferences.likeOrNotSong(id = id)
        }
    }

    override fun getLikedSongs(): Flow<List<Long>> {
        return userPreferences.likedSongs
    }
}
