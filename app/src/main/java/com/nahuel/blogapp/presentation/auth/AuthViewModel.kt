package com.nahuel.blogapp.presentation.auth

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.nahuel.blogapp.core.Resource
import com.nahuel.blogapp.repository.auth.AuthRepo
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class AuthViewModel(private val repo: AuthRepo) : ViewModel() {
    //antes class LoginScreenViewModel

    fun signIn(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Succcess(repo.signIn(email, password)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun signUp(email: String, password: String, username: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Succcess(repo.signUp(email, password, username)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun updateUserProfile(imageBitmap : Bitmap, username : String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Succcess(repo.updateProfile(imageBitmap,username)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

}

class AuthViewModelFactory(private val repo: AuthRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AuthRepo::class.java).newInstance(repo)
        //return LoginScreenViewModel(repo) as T
    }

}