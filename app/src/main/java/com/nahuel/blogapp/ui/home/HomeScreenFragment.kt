package com.nahuel.blogapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nahuel.blogapp.R
import com.nahuel.blogapp.core.Resource
import com.nahuel.blogapp.data.remote.home.HomeScreenDataSource
import com.nahuel.blogapp.databinding.FragmentHomeScreenBinding
import com.nahuel.blogapp.presentation.HomeScreenViewModel
import com.nahuel.blogapp.presentation.HomeScreenViewModelFactory
import com.nahuel.blogapp.repository.home.HomeScreenRepoImpl
import com.nahuel.blogapp.ui.home.adapter.HomeScreenAdapter


class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) {

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
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Succcess -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvHome.adapter = HomeScreenAdapter(result.data)
                }

                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Ocurrio un error: ${result.exception}", Toast.LENGTH_SHORT).show()
                }
            }

        })


    }


}