package com.nahuel.blogapp.repository.home

import com.nahuel.blogapp.core.Resource
import com.nahuel.blogapp.data.model.Post

interface HomeScreenRepo {
    suspend fun getLatestPosts(): Resource<List<Post>>
    suspend fun registerLikeButtonState(postId: String, liked: Boolean)
}