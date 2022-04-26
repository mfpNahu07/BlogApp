package com.nahuel.blogapp.repository

import com.nahuel.blogapp.core.Resource
import com.nahuel.blogapp.data.model.Post

interface HomeScreenRepo {
    suspend fun getLatestPosts(): Resource<List<Post>>
}