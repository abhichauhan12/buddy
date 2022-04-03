package com.example.buddy.ui.home.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.buddy.R
import com.example.buddy.databinding.FragmentFeedsBinding
import com.example.buddy.databinding.FragmentProfileBinding
import com.example.buddy.domain.repo.AuthenticationRepository
import com.example.buddy.domain.repo.FireStoreRepository
import com.example.buddy.ui.authentication.AuthenticationViewModel
import com.example.buddy.ui.home.feeds.Feeds
import com.example.buddy.ui.home.feeds.FeedsAdapter
import com.example.buddy.ui.home.feeds.FeedsViewModel


class Profile : Fragment(R.layout.fragment_profile) {

    companion object{
        fun getInstance()= Profile()
    }

    private lateinit var binding: FragmentProfileBinding

    private val authenticationViewModel by lazy {
        ViewModelProvider(
            this, AuthenticationViewModel.Factory(authenticationRepository = AuthenticationRepository.get())
        )[AuthenticationViewModel::class.java]
    }

    private val profileViewModel by lazy {
        ViewModelProvider(
            this, ProfileViewModel.Factory(fireStoreRepository = FireStoreRepository.get())
        )[ProfileViewModel::class.java]
    }

    private val feedsAdapter : FeedsAdapter by lazy { FeedsAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentProfileBinding.bind(view)
        binding.lifecycleOwner= this

        binding.listItemContainterProfile.adapter=feedsAdapter

        binding.logoutProfile.setOnClickListener {
            authenticationViewModel.logoutUser()
            Toast.makeText(requireContext(), "User Logout", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.splash)
        }

        profileViewModel.userPostFromFirebase(onSuccess = {
            feedsAdapter.submitList(it)
            Toast.makeText(requireContext(), "userpost", Toast.LENGTH_SHORT).show()
        })


    }


}