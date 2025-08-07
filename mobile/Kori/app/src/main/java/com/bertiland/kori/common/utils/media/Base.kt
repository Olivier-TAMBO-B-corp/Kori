package api.ttt.rahisi.template1.utils.media

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import api.ttt.rahisi.template1.utils.media.videos.AsyncVideoPreview
import java.io.File


@Composable
fun AsyncMediaPreview(media: String) {
    if (isImage(media)) {
        ImageWithCoil(
            imagePath = media,
            modifier = Modifier.fillMaxSize()
        )
    } else if (isVideo(media)) {
        AsyncVideoPreview(media)
    } else {
        Text(text = "Unsupported media type", color = Color.Red)
    }
}

// Fonction pour déterminer si le lien est une image
fun isImage(mediaPath: String): Boolean {
    return mediaPath.endsWith(".jpg", true) ||
            mediaPath.endsWith(".jpeg", true) ||
            mediaPath.endsWith(".png", true) ||
            mediaPath.endsWith(".gif", true)
}

// Fonction pour déterminer si le lien est une vidéo
fun isVideo(mediaPath: String): Boolean {
    return mediaPath.endsWith(".mp4", true) ||
            mediaPath.endsWith(".avi", true) ||
            mediaPath.endsWith(".mov", true) ||
            mediaPath.endsWith(".wmv", true)
}

fun getMedias(dir: String): List<String> {
    val directory = File(dir)
    // Vérifiez que le chemin est un répertoire valide
    if (!directory.exists() || !directory.isDirectory) return emptyList()

    // Extensions de fichiers photo et vidéo couramment utilisées
    val mediaExtensions = listOf("jpg", "jpeg", "png", "bmp", "gif", "mp4", "mkv", "mov", "avi")

    // Filtrer les fichiers dans le répertoire en fonction des extensions et trier par date de modification
    return directory.listFiles { file ->
        file.isFile && mediaExtensions.contains(file.extension.lowercase())
    }?.sortedBy { it.lastModified() }?.map { it.absolutePath } ?: emptyList()
}

@Composable
fun HorizontalImageCarousel(images: List<String>) { // Utilise une liste de chemins d'images
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(images) { imagePath ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .aspectRatio(4 / 3f)
                    .padding(0.dp)
                    .clip(MaterialTheme.shapes.medium)
            ) {
                ImageWithCoil(
                    imagePath = imagePath,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
}