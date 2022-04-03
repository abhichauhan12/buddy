package com.example.buddy.ui.home

import android.os.Bundle
import android.provider.Contacts
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.buddy.R
import com.example.buddy.databinding.FragmentHomeViewpagerBinding
import com.example.buddy.ui.home.feeds.Feeds
import com.example.buddy.ui.home.profile.Profile
import com.example.buddy.ui.home.upload.Upload
import com.google.android.material.tabs.TabLayoutMediator

class HomeViewpager : Fragment(R.layout.fragment_home_viewpager){

    private lateinit var binding : FragmentHomeViewpagerBinding
    private val homeViewpagerAdapter by lazy {
        HomeViewpagerAdapter(
            fragmentManager =childFragmentManager,
            lifecycle=lifecycle,
            fragment = listOf(Feeds.getInstance(), Upload.getInstance(), Profile.getInstance())
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentHomeViewpagerBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewPager()
    }

    private fun setUpViewPager() {

        val list = ArrayList<Fragment>()
        list.add(Feeds())
        list.add(Upload())
        list.add(Profile())

        binding.viewpagerHome.adapter= homeViewpagerAdapter

        TabLayoutMediator(binding.tablayoutHomeViewpager, binding.viewpagerHome) { tab, position ->
            tab.text = when(position){
                    0 -> "feeds"
                    1 -> "upload"
                    else -> "profile"
            }
        }.attach()

    }
}