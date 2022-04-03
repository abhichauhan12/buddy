package com.example.buddy.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeViewpagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle : Lifecycle,
    val fragment: List<Fragment>
): FragmentStateAdapter(fragmentManager,lifecycle) {

    override fun getItemCount(): Int = fragment.size

    override fun createFragment(position: Int): Fragment =  fragment[position]


}