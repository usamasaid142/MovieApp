package com.example.movieapp.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.ItemLayoutMoviesBinding
import com.example.movieapp.model.Result


class AllMoviesAdapter () :
    ListAdapter<Result, AllMoviesAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemLayoutMoviesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = getItem(position)

        holder.binding.apply {
            data = result
            ratingBar.rating= result.vote_average?.toFloat()?: 1.0f
        }
    }
    class ViewHolder(itemBinding: ItemLayoutMoviesBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        internal val binding: ItemLayoutMoviesBinding = itemBinding
    }
    private class DiffCallback : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return true
        }
    }

}