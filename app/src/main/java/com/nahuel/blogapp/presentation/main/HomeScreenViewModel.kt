package com.nahuel.blogapp.presentation.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.nahuel.blogapp.core.Resource
import com.nahuel.blogapp.repository.home.HomeScreenRepo
import kotlinx.coroutines.Dispatchers
//
import kotlin.Exception

class HomeScreenViewModel(private val repo: HomeScreenRepo) : ViewModel() {

    fun fetchLatestPost() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getLatestPosts())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }

    }

    fun registerLikeButtonState(postId: String, liked: Boolean) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit( Resource.Loading())


        kotlin.runCatching {
            repo.registerLikeButtonState(postId,liked)
        }.onSuccess {  postList ->
            emit(Resource.Succcess(Unit))
        }.onFailure {  throwable ->
            emit(Resource.Failure(Exception(throwable.message)))
        }
    }
}

//Creamos una instancia del ViewModel a través de la implementación concreta de donde esta
//ubicada la interfaz, por eso no es  repo: HomeScreenRepoImpl

class HomeScreenViewModelFactory(private val repo: HomeScreenRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return modelClass.getConstructor(HomeScreenRepo::class.java).newInstance(repo)
    }

}