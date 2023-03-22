package `in`.ktechnos.mp3player.ui.activity

import `in`.ktechnos.mp3player.domain.model.AudioMetadata

data class AudioPlayerState(

    val isLoading: Boolean = false,
    val audios: List<AudioMetadata> = emptyList(),
    val isPlaying: Boolean = false,
    val selectedAudio: AudioMetadata = AudioMetadata.emptyMetadata(),
    val currentPosition: Int = 0,
    val likedSongs: List<Long> = emptyList()
)
