package com.nahuel.blogapp.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nahuel.blogapp.R
import com.nahuel.blogapp.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment(R.layout.fragment_register) {


    private lateinit var binding: FragmentRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        signUp()
    }

    private fun signUp() {
        binding.btnSignUp.setOnClickListener {
            val username = binding.editTextUsername.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val confirmPassword = binding.editTextConfirmPassword.text.toString().trim()

            if(password != confirmPassword){
                binding.editTextPassword.error ="Password does not match"
                binding.editTextConfirmPassword.error ="Password does not match"
                return@setOnClickListener
            }

            if(username.isEmpty()){
                binding.editTextUsername.error ="Username is empty"
                return@setOnClickListener
            }

            if(email.isEmpty()){
                binding.editTextEmail.error ="Email is empty"
                return@setOnClickListener
            }

            if(password.isEmpty()){
                binding.editTextPassword.error ="Password is empty"
                return@setOnClickListener
            }

            if(confirmPassword.isEmpty()){
                binding.editTextConfirmPassword.error ="Confirm Password is empty"
                return@setOnClickListener
            }

            Log.d("signUpData","$username $email $password $confirmPassword")
        }
    }




}