package com.nahuel.blogapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nahuel.blogapp.R
import com.nahuel.blogapp.data.model.Post
import com.nahuel.blogapp.databinding.FragmentHomeScreenBinding
import com.nahuel.blogapp.ui.home.adapter.HomeScreenAdapter


class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) {

    private lateinit var binding: FragmentHomeScreenBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)


        val postList = listOf(Post(), Post(), Post())


        binding.rvHome.adapter = HomeScreenAdapter(postList)
    }


}