package com.nahuel.blogapp.repository.camera

import android.graphics.Bitmap
import com.nahuel.blogapp.data.remote.camera.CameraDataSource

class CameraRepoImpl(private val dataSource: CameraDataSource): CameraRepo {
    override suspend fun uploadPhoto(imageBitmap: Bitmap, description: String) {
        dataSource.uploadPhoto(imageBitmap, description)

    }


}