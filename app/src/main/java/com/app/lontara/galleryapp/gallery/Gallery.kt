package com.app.lontara.galleryapp.gallery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.lontara.galleryapp.sealed.DataStates
import com.app.lontara.galleryapp.viewmodels.GalleryViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun RenderGallery(viewModel: GalleryViewModel) {

    when (val result = viewModel.response.value) {


        is DataStates.Loading -> {
            Card(modifier = Modifier.fillMaxSize()) {

                CircularProgressIndicator()
            }
        }

        is DataStates.Failure -> {

            Card(modifier = Modifier.fillMaxSize()) {

                Text(text = result.message)
            }
        }

        is DataStates.Data -> {

            ShowImageList(result.images)

        }

        else -> {

            Card(modifier = Modifier.fillMaxSize()) {

                Text(text = "Error loading images")
            }
        }

    }


}

@Composable
fun ShowImageList(image: MutableList<String>) {

    Column(modifier = Modifier.fillMaxSize()) {

        Text(text = "Horizontal List", fontWeight = FontWeight.Bold, fontSize = 20.sp,
            modifier = Modifier.padding(10.dp,0.dp,0.dp,0.dp)
            )


        ShowHorizontalList(image)

        Text(text = "Grids", fontWeight = FontWeight.Bold, fontSize = 20.sp,
        modifier = Modifier.padding(10.dp,0.dp,0.dp,0.dp))


        ShowGrids(image)

    }

}


@Composable
fun ShowHorizontalList(image: MutableList<String>) {

    LazyRow {
        items(image) { images ->
            ShowImageItem(images)
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ShowImageItem(image: String) {

    Card(
        modifier = Modifier
            .height(150.dp)
            .width(150.dp)
            .padding(10.dp)
    ) {

        GlideImage(model = image, contentDescription = "Gallery Image",

            contentScale = ContentScale.Crop

            )
    }
}

@Composable
fun ShowGrids(image: MutableList<String>) {

    LazyVerticalGrid(columns = GridCells.Fixed(3)) {

        items(image) { images ->
            ShowImageItem(images)
        }

    }

}

