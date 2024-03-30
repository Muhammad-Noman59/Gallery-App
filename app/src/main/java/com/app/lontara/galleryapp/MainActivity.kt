package com.app.lontara.galleryapp

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import android.Manifest
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.app.lontara.galleryapp.gallery.RenderGallery
import com.app.lontara.galleryapp.ui.theme.GalleryAppTheme
import com.app.lontara.galleryapp.viewmodels.GalleryViewModel

class MainActivity : ComponentActivity() {

    val viewModel: GalleryViewModel by viewModels()

    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.fetchImagesFromGallery()
        } else {
            Toast.makeText(this, "You must allow this permission to continue.", Toast.LENGTH_SHORT)
                .show()
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GalleryAppTheme {

                Surface(

                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                ) {

                    Column(

                        modifier = Modifier.fillMaxSize()

                    ) {

                        TopAppBar(
                            title = { Text(text = "Gallery") },

                            Modifier.background(color = Color.White),


                            )
                        RenderGallery(viewModel = viewModel)

                    }

                }
            }
            viewModel.fetchImagesFromGallery()
            if (permissionCheck())
                viewModel.fetchImagesFromGallery()
            else
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }


    private fun permissionCheck(): Boolean {

        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GalleryAppTheme {
        Greeting("Android")
    }
}