package com.nahuel.blogapp.repository.auth

import com.google.firebase.auth.FirebaseUser

interface LoginRepo {

    suspend fun signIn(email:String,password:String): FirebaseUser?

}