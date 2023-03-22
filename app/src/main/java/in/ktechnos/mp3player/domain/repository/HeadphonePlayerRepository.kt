package `in`.ktechnos.mp3player.domain.repository


import `in`.ktechnos.mp3player.domain.model.AudioMetadata
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface HeadphonePlayerRepository {

    suspend fun loadCoverBitmap(context: Context, uri: Uri): Bitmap?

    suspend fun getAudios(): List<AudioMetadata>

    suspend fun likeOrNotSong(id: Long)

    fun getLikedSongs(): Flow<List<Long>>
}