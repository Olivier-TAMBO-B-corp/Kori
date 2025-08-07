package api.ttt.rahisi.template1.utils.media

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.io.File

// Listener to handle the loaded resource
fun interface ResourceReadyListener {
    fun onResourceReady(uri: Uri?)
}

class TFileLoader(private val activity: ComponentActivity, private val fragment: Fragment? = null) {

    private val context = activity.applicationContext

    // Launchers for activity results
    private lateinit var loadImageLauncher: ActivityResultLauncher<Intent>
    private lateinit var loadVideoLauncher: ActivityResultLauncher<Intent>
    private lateinit var loadMediaLauncher: ActivityResultLauncher<Intent>
    private lateinit var loadFileLauncher: ActivityResultLauncher<Intent>
    private lateinit var recordAudioLauncher: ActivityResultLauncher<Intent>

    init {
        setupLaunchers()
    }

    private fun setupLaunchers() {
        val owner = fragment ?: activity

        loadImageLauncher = owner.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                listener?.onResourceReady(uri)
            }
        }

        loadVideoLauncher = owner.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                listener?.onResourceReady(uri)
            }
        }

        loadMediaLauncher = owner.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                listener?.onResourceReady(uri)
            }
        }

        loadFileLauncher = owner.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                listener?.onResourceReady(uri)
            }
        }

        recordAudioLauncher = owner.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                listener?.onResourceReady(uri)
            }
        }
    }

    private var listener: ResourceReadyListener? = null

    // Function to request permissions
    private fun requestPermission(permission: String, rationale: String, onGranted: () -> Unit) {
        if (ContextCompat.checkSelfPermission(context, permission) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            onGranted()
        } else {
            ActivityCompat.requestPermissions(activity, arrayOf(permission), 1)
        }
    }

    // Load image from gallery
    fun loadImageFromGallery(listener: ResourceReadyListener) {
        this.listener = listener
        requestPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE, "Storage permission is required to load images") {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            loadImageLauncher.launch(intent)
        }
    }

    // Load video from gallery
    fun loadVideoFromGallery(listener: ResourceReadyListener) {
        this.listener = listener
        requestPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE, "Storage permission is required to load videos") {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            loadVideoLauncher.launch(intent)
        }
    }

    // Load media (image or video) from gallery
    fun loadMediaFromGallery(listener: ResourceReadyListener) {
        this.listener = listener
        requestPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE, "Storage permission is required to load media") {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Files.getContentUri("external"))
            loadMediaLauncher.launch(intent)
        }
    }

    // Load file from file manager
    fun loadFile(listener: ResourceReadyListener) {
        this.listener = listener
        requestPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE, "Storage permission is required to load files") {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "*/*"
            }
            loadFileLauncher.launch(intent)
        }
    }

    // Record audio from microphone
    fun recordAudio(listener: ResourceReadyListener) {
        this.listener = listener
        requestPermission(android.Manifest.permission.RECORD_AUDIO, "Microphone permission is required to record audio") {
            val intent = Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION)
            recordAudioLauncher.launch(intent)
        }
    }
}
