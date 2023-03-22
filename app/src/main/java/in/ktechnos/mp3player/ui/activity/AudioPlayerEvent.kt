package `in`.ktechnos.mp3player.ui.activity

import `in`.ktechnos.mp3player.domain.model.AudioMetadata
import android.content.Context

sealed class AudioPlayerEvent{

    data class InitAudio(
        val audio: AudioMetadata,
        val context: Context,
        val onAudioInitialized: () -> Unit
    ): AudioPlayerEvent()

    data class Seek(val position: Float): AudioPlayerEvent()

    data class LikeOrNotSong(val id: Long): AudioPlayerEvent()

    object Play: AudioPlayerEvent()

    object Pause: AudioPlayerEvent()

    object Stop: AudioPlayerEvent()

    object HideLoadingDialog: AudioPlayerEvent()

    object LoadMedias: AudioPlayerEvent()
}
