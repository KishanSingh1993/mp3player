package `in`.ktechnos.mp3player.ui.activity

import `in`.ktechnos.mp3player.domain.model.AudioMetadata
import `in`.ktechnos.mp3player.domain.repository.HeadphonePlayerRepository
import `in`.ktechnos.mp3player.util.audio.VisualizerData
import `in`.ktechnos.mp3player.util.audio.VisualizerHelper
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



@HiltViewModel
class MainViewModel @Inject constructor(private val repository: HeadphonePlayerRepository):ViewModel() {

    private var _state by mutableStateOf(value = AudioPlayerState())
    val state: AudioPlayerState
        get() = _state

    private val _visualizerData =
        mutableStateOf(value = VisualizerData.emptyVisualizerData())
    val visualizerData: State<VisualizerData>
        get() = _visualizerData

    private var _player: MediaPlayer? = null

    private val _visualizerHelper = VisualizerHelper()

    private val _handler = Handler(Looper.getMainLooper())

    init {
        loadMedias()
    }


    fun onEvent(event: AudioPlayerEvent) {
        when (event) {

            is AudioPlayerEvent.InitAudio -> initAudio(
                audio = event.audio,
                context = event.context,
                onAudioInitialized = event.onAudioInitialized
            )

            is AudioPlayerEvent.Seek -> seek(position = event.position)

            is AudioPlayerEvent.LikeOrNotSong -> likeOrNotSong(id = event.id)

            AudioPlayerEvent.Pause -> pause()

            AudioPlayerEvent.Play -> play()


            AudioPlayerEvent.Stop -> stop()

            AudioPlayerEvent.HideLoadingDialog -> hideLoadingDialog()

            AudioPlayerEvent.LoadMedias -> loadMedias()
        }
    }

    private fun initAudio(audio: AudioMetadata, context: Context, onAudioInitialized: () -> Unit) {
        viewModelScope.launch {
            _state = _state.copy(isLoading = true)

            delay(800)

            val cover = repository.loadCoverBitmap(
                context = context,
                uri = audio.contentUri
            )

            _state = _state.copy(selectedAudio = audio.copy(cover = cover))

            _player = MediaPlayer().apply {
                setDataSource(context, audio.contentUri)
                prepare()
            }

            _player?.setOnCompletionListener {
                pause()
            }

            _player?.setOnPreparedListener {
                onAudioInitialized()
            }

            _state = _state.copy(isLoading = false)
        }
    }

    private fun play() {
        _state = _state.copy(isPlaying = true)

        _player?.start()

        _player?.run {
            _visualizerHelper.start(audioSessionId = audioSessionId, onData = { data ->
                _visualizerData.value = data
            })
        }
        _handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    _state = _state.copy(currentPosition = _player!!.currentPosition)
                    _handler.postDelayed(this, 1000)
                } catch (exp: Exception) {
                    _state = _state.copy(currentPosition = 0)
                }
            }

        }, 0)
    }

    private fun pause() {
        _state = _state.copy(isPlaying = false)
        _visualizerHelper.stop()
        _player?.pause()
    }

    private fun stop() {
        _visualizerHelper.stop()
        _player?.stop()
        _player?.reset()
        _player?.release()
        _state = _state.copy(
            isPlaying = false,
            currentPosition = 0
        )
        _player = null
    }

    private fun seek(position: Float) {
        _player?.run {
            seekTo(position.toInt())
        }
    }

    private fun loadMedias() {
        viewModelScope.launch {
            _state = _state.copy(isLoading = true)
            val audios = mutableStateListOf<AudioMetadata>()
            audios.addAll(prepareAudios())
            _state = _state.copy(audios = audios)
            repository.getLikedSongs().collect { likedSongs ->
                _state = _state.copy(
                    likedSongs = likedSongs,
                    isLoading = false,
                )
            }
        }
    }

    private suspend fun prepareAudios(): List<AudioMetadata> {
        return repository.getAudios().map {
            val artist = if (it.artist.contains("<unknown>"))
                "Unknown artist" else it.artist
            it.copy(artist = artist)
        }
    }

    private fun hideLoadingDialog() {
        _state = _state.copy(isLoading = false)
    }

    private fun likeOrNotSong(id: Long) {
        viewModelScope.launch {
            repository.likeOrNotSong(id = id)
        }
    }
}