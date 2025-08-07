package api.ttt.rahisi.template1.utils.media

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bertiland.kori.R
import java.io.File


@Composable
fun ImageWithCoil(imagePath: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val imageLoader = ImageLoader(context)

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(File(imagePath)) // Utilisation correcte du fichier local
            .crossfade(true)
            .placeholder(R.drawable.placeholder) // Placeholder en cas de chargement
            .error(R.drawable.error) // Image d'erreur si l'image ne charge pas
            .build(),
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop // Ajuste l'image au conteneur
    )
}

@Composable
fun AsyncImageLoader(
    imagePath: String? = null,
    imageRes: Int = -1,
    imageVector: ImageVector? = null, // Ajout de imageVector comme paramètre optionnel
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Condition pour vérifier si une image vectorielle est fournie
    if (imageVector != null) {
        // Si un ImageVector est passé, nous l'affichons directement
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = modifier
        )
    } else {
        // Sinon, on charge l'image comme auparavant (fichier local ou ressource)
        val imageRequest = ImageRequest.Builder(context).apply {
            when {
                imageRes != -1 -> data(imageRes) // Charge l'image depuis les ressources
                imagePath != null -> data(File(imagePath)) // Charge l'image depuis un fichier local
            }
            crossfade(true)
            placeholder(R.drawable.placeholder) // Placeholder en cas de chargement
            error(R.drawable.error) // Image d'erreur si l'image ne charge pas
        }.build()

        AsyncImage(
            model = imageRequest,
            contentDescription = null,
            modifier = modifier,
            contentScale = ContentScale.Crop // Ajuste l'image au conteneur
        )
    }
}