package com.example.buddy.ui.home.feeds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.buddy.R
import com.example.buddy.databinding.FragmentFeedsBinding
import com.example.buddy.domain.repo.AuthenticationRepository
import com.example.buddy.domain.repo.FireStoreRepository
import com.example.buddy.ui.authentication.AuthenticationViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class Feeds : Fragment(R.layout.fragment_feeds) {

    companion object{ fun getInstance()=Feeds() }

    private lateinit var binding:FragmentFeedsBinding

    private val feedsViewModel by lazy { ViewModelProvider(
            this, FeedsViewModel.Factory(fireStoreRepository = FireStoreRepository.get()))[FeedsViewModel::class.java]
    }

    private val feedsAdapter : FeedsAdapter by lazy { FeedsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentFeedsBinding.bind(view)
        binding.lifecycleOwner= this
        binding.listItemContainter.adapter=feedsAdapter


        lifecycleScope.launch {
            feedsViewModel.fetchpost.collect {
                feedsAdapter.submitList(it)
            }
        }

        feedsViewModel.postFromFirebase(onSuccess = {
        })


    }
}