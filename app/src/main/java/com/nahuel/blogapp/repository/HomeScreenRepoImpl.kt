package com.nahuel.blogapp.repository

import com.nahuel.blogapp.core.Resource
import com.nahuel.blogapp.data.model.Post

class HomeScreenRepoImpl: HomeScreenRepo {
    override suspend fun getLatestPosts(): Resource<Post> {
        TODO("Not yet implemented")
    }
}