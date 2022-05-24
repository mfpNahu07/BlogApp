package com.nahuel.blogapp.ui.camera

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nahuel.blogapp.R
import com.nahuel.blogapp.core.Resource
import com.nahuel.blogapp.data.remote.camera.CameraDataSource
import com.nahuel.blogapp.data.remote.home.HomeScreenDataSource
import com.nahuel.blogapp.databinding.FragmentCameraBinding
import com.nahuel.blogapp.presentation.camera.CameraViewModel
import com.nahuel.blogapp.presentation.camera.CameraViewModelFactory
import com.nahuel.blogapp.presentation.main.HomeScreenViewModel
import com.nahuel.blogapp.presentation.main.HomeScreenViewModelFactory
import com.nahuel.blogapp.repository.camera.CameraRepoImpl
import com.nahuel.blogapp.repository.home.HomeScreenRepoImpl


class CameraFragment : Fragment(R.layout.fragment_camera) {


    private lateinit var binding: FragmentCameraBinding
    private var bitmap: Bitmap? = null
    private val viewModel by viewModels<CameraViewModel> {
        CameraViewModelFactory(CameraRepoImpl(CameraDataSource()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCameraBinding.bind(view)

        binding.btnUploadPhoto.setOnClickListener {
            bitmap?.let { it ->
                viewModel.uploadPhoto(it,binding.editTextPhotoDescription.text.toString().trim()).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Resource.Loading ->{
                            Toast.makeText(requireContext(), "Uploading photo...", Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Succcess ->{
                            findNavController().navigate(R.id.action_cameraFragment_to_homeScreenFragment)
                        }
                        is Resource.Failure ->{
                            Toast.makeText(requireContext(), "Error: ${result.exception}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {

                    val imageBitmap = result.data?.extras?.get("data") as Bitmap
                    binding.imgAddPhoto.setImageBitmap(imageBitmap)
                    bitmap = imageBitmap

                }
            }

        try {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            resultLauncher.launch(takePictureIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(),
                "No se encontro una aplicacion de camara",
                Toast.LENGTH_LONG
            ).show()
        }
    }

}