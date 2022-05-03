package com.nahuel.blogapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.nahuel.blogapp.R
import com.nahuel.blogapp.databinding.FragmentLoginBinding


class LoginFragment : Fragment(R.layout.fragment_login) {

private lateinit var binding: FragmentLoginBinding
private val firebaseAuth by lazy{ FirebaseAuth.getInstance()}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        isUserLoggedIn()
        doLogin()

    }

    //Si currentUser es null, queire decir que el usuario no esta autenticado
    private fun isUserLoggedIn(){
        firebaseAuth.currentUser?.let {
            findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
        }
    }

    private fun doLogin(){
        binding.btnSignIn.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            validateCredentials(email,password)
            signIn(email,password)
        }
    }

    private fun validateCredentials(email: String, password: String){
        if(email.isEmpty()){
            binding.editTextEmail.error = "Email is empty"
            return
        }

        if(password.isEmpty()){
            binding.editTextPassword.error = "Password is empty"
            return
        }
    }

    private fun signIn(email: String, password: String){

    }



}