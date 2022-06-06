package com.nahuel.blogapp.data.remote.home

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.nahuel.blogapp.core.Resource
import com.nahuel.blogapp.data.model.Post
import kotlinx.coroutines.tasks.await


class HomeScreenDataSource {
    suspend fun getLatestPost(): Resource<List<Post>> {

        val postList = mutableListOf<Post>()

        val querySnapshot = FirebaseFirestore.getInstance().collection("posts")
            .orderBy("created_at", Query.Direction.DESCENDING)
            .get().await()
        for (post in querySnapshot.documents) {
            post.toObject(Post::class.java)?.let {

                val isLiked = FirebaseAuth.getInstance().currentUser?.let { safeUser ->
                    isPostLiked(post.id, safeUser.uid)
                }

                it.apply {
                    created_at = post.getTimestamp(
                        "created_at",
                        DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                    )?.toDate()
                    id = post.id

                    if (isLiked != null) {
                        liked = isLiked
                    }

                }
                postList.add(it)
            }
        }
        return Resource.Succcess(postList)
    }

    private suspend fun isPostLiked(postId: String, uid: String): Boolean {
        val post =
            FirebaseFirestore.getInstance().collection("postLikes").document(postId).get().await()
        if (!post.exists()) return false
        val likeArray: List<String> = post.get("likes") as List<String>
        return likeArray.contains(uid)


    }

    fun registerLikeButtonState(postId: String, liked: Boolean) {
        val increment = FieldValue.increment(1)
        val decrement = FieldValue.increment(-1)


        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val postRef = FirebaseFirestore.getInstance().collection("posts").document(postId)
        val postLikesRef = FirebaseFirestore.getInstance().collection("postLikes").document(postId)

        val database = FirebaseFirestore.getInstance()

        database.runTransaction { transaction ->
            val snapshot = transaction.get(postRef)
            val likeCount = snapshot.getLong("likes")
            if (likeCount != null) {
                if (likeCount >= 0) {
                    if (liked) {
                        if (transaction.get(postLikesRef).exists()) {
                            transaction.update(postLikesRef, "likes", FieldValue.arrayUnion(uid))
                        } else {
                            transaction.set(
                                postLikesRef,
                                hashMapOf("likes" to arrayListOf(uid)),
                                SetOptions.merge()
                            )
                        }

                        transaction.update(postRef, "likes", increment)
                    } else {
                        transaction.update(postRef, "likes", decrement)
                        transaction.update(postLikesRef, "likes", FieldValue.arrayRemove(uid))
                    }
                }
            }
        }.addOnSuccessListener {

        }.addOnFailureListener {
            throw Exception(it.message)
        }
    }

}