package com.example.movieapp.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.ItemLayoutMoviesBinding
import com.example.movieapp.model.MovieId
import com.example.movieapp.model.Result


class AllMoviesAdapter (val listener:IMoviesListener,val moviesList:List<MovieId>) :
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
        }
        holder.binding.layoutCard.setOnClickListener {
            listener.onItemClicked(result)
        }
        holder.binding.fab.setOnClickListener {
            listener.addToFavorite(result)
        }

        moviesList.forEach {
            if (result.id==it.id){
                holder.binding.ivFav.visibility=View.VISIBLE
                holder.binding.fab.visibility=View.GONE
            }
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

    interface IMoviesListener{
        fun onItemClicked(result:Result)
        fun addToFavorite(result: Result)
    }

}