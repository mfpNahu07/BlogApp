package com.nahuel.blogapp.presentation.camera

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.nahuel.blogapp.core.Resource
import com.nahuel.blogapp.repository.auth.AuthRepo
import com.nahuel.blogapp.repository.camera.CameraRepo
import kotlinx.coroutines.Dispatchers

class CameraViewModel(private val repo: CameraRepo) : ViewModel() {

    fun uploadPhoto(imageBitmap: Bitmap, description: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Succcess(repo.uploadPhoto(imageBitmap,description)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }

    }

}

class CameraViewModelFactory(private val repo: CameraRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CameraRepo::class.java).newInstance(repo)
        //return LoginScreenViewModel(repo) as T
    }
}