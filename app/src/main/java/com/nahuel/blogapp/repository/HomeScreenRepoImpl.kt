package com.nahuel.blogapp.repository

import com.nahuel.blogapp.core.Resource
import com.nahuel.blogapp.data.model.Post
import com.nahuel.blogapp.data.remote.HomeScreenDataSource

class HomeScreenRepoImpl(private val dataSource: HomeScreenDataSource) : HomeScreenRepo {
    override suspend fun getLatestPosts(): Resource<List<Post>> =
        dataSource.getLatestPost()

}