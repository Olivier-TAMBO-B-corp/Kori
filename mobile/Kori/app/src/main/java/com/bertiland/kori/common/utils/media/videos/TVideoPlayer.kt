package api.ttt.rahisi.template1.utils.media.videos

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.media3.exoplayer.ExoPlayer

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource

object TVideoPlayer {

    // HashMap pour stocker les instances ExoPlayer par lien de média
    private val exoPlayerMap = mutableMapOf<String, ExoPlayer>()
    private var currentMediaLink: String? = null

    private var currentPlayingPlayer: ExoPlayer? = null




    @androidx.annotation.OptIn(UnstableApi::class)
    fun getPreparedPlayer(context: Context, mediaLink: String): ExoPlayer {
        var exoPlayer: ExoPlayer? = null
        var currentMediaLink: String? = null

        // Libère l'instance actuelle si elle existe
        /*if (exoPlayer != null) {
            exoPlayer?.playWhenReady = false
            exoPlayer?.pause()
            exoPlayer?.stop()
            exoPlayer?.release()
            exoPlayer = null
        }*/

        // Crée une nouvelle instance d'ExoPlayer
        exoPlayer = getPlayer(context, mediaLink)///ExoPlayer.Builder(context).build()

        // Charger et préparer la nouvelle source média
        val mediaSource = getMediaSource(context, mediaLink)
        exoPlayer!!.setMediaSource(mediaSource!!)
        exoPlayer!!.prepare()
        exoPlayer.repeatMode = Player.REPEAT_MODE_ONE

        exoPlayer!!.playWhenReady = true

        // Mémorise le lien actuel pour éviter les recharges répétées
        currentMediaLink = mediaLink

        return exoPlayer!!
    }

    /**
     * Récupère ou crée un ExoPlayer pour le média spécifié.
     * Met en pause tous les autres lecteurs avant de retourner ou de créer le lecteur demandé.
     */
    fun getPlayer(context: Context, mediaLink: String): ExoPlayer {
        // Mettre tous les autres lecteurs en pause
        pause()

        // Vérifie si un ExoPlayer existe déjà pour ce mediaLink
        /*val player = exoPlayerMap[mediaLink] ?: ExoPlayer.Builder(context).build().apply {
            exoPlayerMap[mediaLink] = this
        }*/
        val player = ExoPlayer.Builder(context).build().apply {
            exoPlayerMap[mediaLink] = this
        }

        return player
    }

    fun isPlayerReady(player: ExoPlayer): Boolean {
        return player.playbackState == Player.STATE_READY
    }

    /**
     * Met en pause tous les lecteurs actifs.
     */
    fun pause() {
        exoPlayerMap.values.forEach {
            if(it != null) {
                it.playWhenReady = false
                it.pause()

                it.stop()
                it.release()
            }
        }
    }

    fun play(player : ExoPlayer) {
        ///pause()

        // Si un autre lecteur joue, mettez-le en pause
        if (currentPlayingPlayer != player) {
            pause()
            currentPlayingPlayer = player // Met à jour le lecteur en cours
            player.play() // Lance la lecture pour le nouveau lecteur
        } else if (!player.isPlaying) {
            player.play() // Si le lecteur actuel est en pause, relance la lecture
        }
    }

    /**
     * Libère tous les lecteurs lorsqu'ils ne sont plus nécessaires.
     * Appeler cette fonction lors de la fermeture de l'application ou d'un nettoyage global.
     */
    fun release() {
        /*exoPlayerMap.values.forEach { it.release() }
        exoPlayerMap.clear()
        currentMediaLink = null*/
    }

    /**---------------------------------------------------------------------------------*/

    @SuppressLint("UnsafeOptInUsageError")
    fun getMediaSource(context: Context, mediaLink: String): MediaSource? {
        val dataSourceFactory = DefaultDataSource.Factory(context)

        return if (mediaLink.startsWith("http://") || mediaLink.startsWith("https://")) {
            // MediaSource pour une vidéo en ligne
            ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(mediaLink))
        } else {
            // MediaSource pour une vidéo locale
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED) {
                // Ici, vous devriez obtenir l'URI du fichier de manière appropriée
                val uri = Uri.parse(mediaLink)
                ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(uri))
            } else {
                Toast.makeText(context, "Ceci est un Toast dans Compose !", Toast.LENGTH_SHORT).show()
                null
            }
        }
    }

    /**---------------------------------------------------------------------------------*/
}