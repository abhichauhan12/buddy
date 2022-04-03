package com.example.buddy.ui.home.feeds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.buddy.databinding.ListItemFeedsBinding
import com.example.buddy.domain.models.Posts

class FeedsAdapter () : ListAdapter<Posts, FeedsAdapter.PostViewHolder>(DiffUtilCallback) {

    object DiffUtilCallback : DiffUtil.ItemCallback<Posts>() {
        override fun areItemsTheSame(oldItem: Posts, newItem: Posts): Boolean {
            return oldItem.imageUrl == newItem.imageUrl
        }

        override fun areContentsTheSame(oldItem: Posts, newItem: Posts): Boolean {
            return oldItem == newItem
        }
    }


    inner class PostViewHolder(private val binding: ListItemFeedsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post : Posts) {
            binding.postitem = post
            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedsAdapter.PostViewHolder {
        return PostViewHolder(ListItemFeedsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FeedsAdapter.PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}