package com.nahuel.blogapp.data.remote.auth

import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.nahuel.blogapp.data.model.User
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream

class AuthDataSource {

    suspend fun signIn(email: String, password: String): FirebaseUser? {

        val authResult = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()

        return authResult.user
    }

   suspend fun signUp(email: String, password: String, username: String): FirebaseUser? {
       val authResult = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).await()
       authResult.user?.uid?.let { uid ->
           FirebaseFirestore.getInstance().collection("users").document(uid).set(User(username,email)).await()
       }
       return authResult.user
    }

    suspend fun updateUserProfile(imageBitmap: Bitmap, username:String){

        val user = FirebaseAuth.getInstance().currentUser
        val imageRef = FirebaseStorage.getInstance().reference.child("${user?.uid}/profile_picture")
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        //Cargamos la foto en Firebase y aguardamos a que la foto se cargue, luego obtenemos la url de descarga
        val downloadUrl = imageRef.putBytes(baos.toByteArray()).await().storage.downloadUrl.await().toString()

        val profileUpdates =  UserProfileChangeRequest.Builder() //Se guardan los datos en el usuario (token de autenticacion), no en la base de datos
            .setDisplayName(username)
            .setPhotoUri(Uri.parse(downloadUrl))
            .build()
        user?.updateProfile(profileUpdates)?.await()
    }


}