package com.nahuel.blogapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nahuel.blogapp.R
import com.nahuel.blogapp.core.Resource
import com.nahuel.blogapp.core.hide
import com.nahuel.blogapp.core.show
import com.nahuel.blogapp.data.model.Post
import com.nahuel.blogapp.data.remote.home.HomeScreenDataSource
import com.nahuel.blogapp.databinding.FragmentHomeScreenBinding
import com.nahuel.blogapp.presentation.main.HomeScreenViewModel
import com.nahuel.blogapp.presentation.main.HomeScreenViewModelFactory
import com.nahuel.blogapp.repository.home.HomeScreenRepoImpl
import com.nahuel.blogapp.ui.home.adapter.HomeScreenAdapter
import com.nahuel.blogapp.ui.home.adapter.OnPostClickListener


class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) , OnPostClickListener{

    private lateinit var binding: FragmentHomeScreenBinding
    private val viewModel by viewModels<HomeScreenViewModel> {
        HomeScreenViewModelFactory(HomeScreenRepoImpl(HomeScreenDataSource()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)

        viewModel.fetchLatestPost().observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Resource.Loading ->{
                   // binding.progressBar.visibility = View.VISIBLE
                    binding.progressBar.show()
                }

                is Resource.Succcess -> {
                    binding.progressBar.hide()
                    if(result.data.isEmpty()){
                        binding.emptyContainer.show()
                        return@Observer
                    }else{
                        binding.emptyContainer.hide()
                    }
                    binding.rvHome.adapter = HomeScreenAdapter(result.data,this)
                }

                is Resource.Failure -> {
                    binding.progressBar.hide()
                    Toast.makeText(requireContext(), "Ocurrio un error: ${result.exception}", Toast.LENGTH_SHORT).show()
                }
            }

        })


    }

    override fun onLikedButtonClick(post: Post, liked: Boolean) {
        TODO("Not yet implemented")
    }


}