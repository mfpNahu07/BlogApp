package com.nahuel.blogapp.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nahuel.blogapp.R
import com.nahuel.blogapp.core.BaseViewHolder
import com.nahuel.blogapp.core.TimeAgo
import com.nahuel.blogapp.data.model.Post
import com.nahuel.blogapp.databinding.PostItemViewBinding

class HomeScreenAdapter(private val postList: List<Post>, private val onPostClickListener: OnPostClickListener) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var postClickListener: OnPostClickListener? = null


    init {
        postClickListener = onPostClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            PostItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeScreenViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is HomeScreenViewHolder -> holder.bind(postList[position])
        }

    }

    override fun getItemCount(): Int {
        return postList.size
    }

    private inner class HomeScreenViewHolder(
        val binding: PostItemViewBinding,
        val context: Context
    ) : BaseViewHolder<Post>(binding.root) {
        override fun bind(item: Post) {
            setupProfileInfo(item)
            addPostTimeStamp(item)
            setupPostImage(item)
            setupPostDescription(item)
            tintHeartIcon(item)
            setupLikeCount(item)

        }

        private fun setupProfileInfo(post : Post){
            Glide.with(context).load(post.poster?.profile_picture).centerCrop().into(binding.profilePicture)
            binding.profileName.text = post.poster?.username
        }

        private fun addPostTimeStamp(post : Post){
            //Obtenemos el tiempo del servidor, lo transformo a time( milisegundos) y lo divido en 1000 para obtener los segundos
            val createdAt = (post.created_at?.time?.div(1000L))?.let {
                TimeAgo.getTimeAgo(it.toInt())
            }
            binding.postTimestamp.text = createdAt
        }

        private fun setupPostImage(post:Post){
            Glide.with(context).load(post.post_image).centerCrop().into(binding.postImage)
        }

        private fun setupPostDescription(post:Post){
            if (post.post_description.isEmpty()) {
                binding.postDescription.visibility = View.GONE
            } else {
                binding.postDescription.text = post.post_description
            }
        }

        private fun tintHeartIcon(post:Post){
            if(!post.liked){
                binding.likeBtn.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_favorite_border_24))
            }else{
                binding.likeBtn.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_favorite_24))
            }
        }

        private fun setupLikeCount(post: Post){
            if(post.likes > 0){
                binding.likeCount.visibility = View.VISIBLE
                binding.likeCount.text = "${post.likes} likes"
            } else{
                binding.likeCount.visibility = View.GONE
            }
        }

        private fun setLikeClickAction(post: Post){
            binding.likeBtn.setOnClickListener {
                if(post.liked) post.apply { liked = false } else post.apply { liked = true }
                tintHeartIcon(post)
                postClickListener?.onLikedButtonClick(post, post.liked)
            }
        }
    }


}

interface OnPostClickListener{
    fun onLikedButtonClick(post:Post, liked: Boolean)
}







//Encargado de recibir cada uno de los posts