package com.app.lontara.galleryapp.viewmodels

import android.app.Application
import android.provider.MediaStore
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.app.lontara.galleryapp.sealed.DataStates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class GalleryViewModel(application: Application) : AndroidViewModel(application) {

    var response: MutableState<DataStates> = mutableStateOf(DataStates.Empty)

    private val context by lazy {
        application.applicationContext
    }


    fun fetchImagesFromGallery() {

        response.value = DataStates.Loading

        val galleryImageUrls = arrayListOf<String>()

        viewModelScope.launch(Dispatchers.IO) {

            val loadingImage = async {


                val columns = arrayOf(
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media._ID
                ) //get all columns of type images
                val orderBy = MediaStore.Images.Media.DATE_TAKEN //order data by date

                val imagecursor = context.contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                    null, "$orderBy DESC"
                ) //get all data in Cursor by sorting in DESC order

                imagecursor!!.moveToFirst()
                while (!imagecursor.isAfterLast) {
                    val dataColumnIndex =
                        imagecursor.getColumnIndex(MediaStore.Images.Media.DATA) //get column index
                    galleryImageUrls.add(imagecursor.getString(dataColumnIndex)) //get Image from column index

                    imagecursor.moveToNext()
                }

            }


            loadingImage.await()

            if (galleryImageUrls.isEmpty()){

                response.value = DataStates.Failure("No image available to show.")
            }else{

                response.value = DataStates.Data(galleryImageUrls)

            }

        }


    }
}