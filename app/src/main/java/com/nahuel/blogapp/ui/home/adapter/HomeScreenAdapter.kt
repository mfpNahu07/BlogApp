package com.nahuel.blogapp.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nahuel.blogapp.core.BaseViewHolder
import com.nahuel.blogapp.core.TimeAgo
import com.nahuel.blogapp.data.model.Post
import com.nahuel.blogapp.databinding.PostItemViewBinding

class HomeScreenAdapter(private val postList: List<Post>) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

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
            Glide.with(context).load(item.post_image).centerCrop().into(binding.postImage)
            Glide.with(context).load(item.profile_picture).centerCrop().into(binding.profilePicture)
            binding.profileName.text = item.profile_name
            if (item.post_description.isEmpty()) {
                binding.postDescription.visibility = View.GONE
            } else {
                binding.postDescription.text = item.post_description
            }


            //Obtenemos el tiempo del servidor, lo transformo a time( milisegundos) y lo divido en 1000 para obtener los segundos
            val createdAt = (item.created_at?.time?.div(1000L))?.let {
                TimeAgo.getTimeAgo(it.toInt())
            }
            binding.postTimestamp.text = createdAt
        }
    }

}

//Encargado de recibir cada uno de los posts