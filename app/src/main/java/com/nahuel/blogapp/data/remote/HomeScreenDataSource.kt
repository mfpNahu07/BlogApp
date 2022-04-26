package com.nahuel.blogapp.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.nahuel.blogapp.core.Resource
import com.nahuel.blogapp.data.model.Post
import kotlinx.coroutines.tasks.await

class HomeScreenDataSource {
    suspend fun getLatestPost(): Resource<List<Post>> {

        val postList = mutableListOf<Post>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("posts").get().await()
        for( post in querySnapshot.documents){
            post.toObject(Post::class.java)?.let {
                postList.add(it)
            }
        }
        return Resource.Succcess(postList)
    }

}