package com.nahuel.blogapp.ui.auth

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nahuel.blogapp.R
import com.nahuel.blogapp.core.Resource
import com.nahuel.blogapp.data.remote.auth.AuthDataSource
import com.nahuel.blogapp.databinding.FragmentSetUpProfileBinding
import com.nahuel.blogapp.presentation.auth.AuthViewModel
import com.nahuel.blogapp.presentation.auth.AuthViewModelFactory
//import com.nahuel.blogapp.presentation.auth.LoginScreenViewModel
import com.nahuel.blogapp.repository.auth.AuthRepoImpl


class SetUpProfileFragment : Fragment(R.layout.fragment_set_up_profile) {

    private lateinit var binding: FragmentSetUpProfileBinding


    private val viewModel by viewModels<AuthViewModel> {
    AuthViewModelFactory(
    AuthRepoImpl(
    AuthDataSource()
    )
    )
    }

    private var bitmap: Bitmap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSetUpProfileBinding.bind(view)

        // binding.profileImage.setOnClickListener
        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {

                    val imageBitmap = result.data?.extras?.get("data") as Bitmap
                    binding.profileImage.setImageBitmap(imageBitmap)
                    bitmap = imageBitmap
                    Toast.makeText(
                        requireContext(),
                        "Entramos en registerfor activity",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

        binding.profileImage.setOnClickListener {
            try {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                resultLauncher.launch(takePictureIntent)
                Toast.makeText(requireContext(), "Entramos en launch", Toast.LENGTH_SHORT).show()
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    requireContext(),
                    "No se encontro una aplicacion de camara",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.btnCreateProfile.setOnClickListener {

            Toast.makeText(requireContext(), "Entramos en create profile", Toast.LENGTH_SHORT)
                .show()


            val username = binding.edittextUsername.text.toString().trim()
            val alertDialog = AlertDialog.Builder(requireContext()).setTitle("Uploading Photo").create()

            if (bitmap != null && username.isNotEmpty()) {
            // viewModel.updateUserProfile(imageBitmap = bitmap!!, username= username)
            viewModel.updateUserProfile(bitmap!!, username)
            .observe(viewLifecycleOwner) { result ->
            when (result) {
            is Resource.Loading -> {
            alertDialog.show()
            }
            is Resource.Succcess -> {
            alertDialog.dismiss()
            findNavController().navigate(R.id.action_setUpProfileFragment_to_homeScreenFragment)
            }
            is Resource.Failure -> {
            alertDialog.dismiss()}

            }
            }

            }

        }

    }
}