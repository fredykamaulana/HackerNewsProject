package com.fmyapp.storylist.presentation.storylist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fmyapp.core.presentation.abstraction.OnItemClickListener
import com.fmyapp.core.presentation.model.ItemUiModel
import com.fmyapp.storylist.databinding.LayoutItemStoryBinding

class StoryListAdapter(private val listener: OnItemClickListener) :
    ListAdapter<ItemUiModel, StoryListAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.create(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class ViewHolder(private val binding: LayoutItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: ItemUiModel, listener: OnItemClickListener) {
            binding.tvStoryItemTitle.text = item.title
            binding.tvStoryItemCommentsCount.text = "Comment : ${item.descendants}"
            binding.tvStoryItemScore.text = "Score : ${item.score}"

            binding.cardStoryItem.setOnClickListener {
                listener.onClick(item.id)
            }
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = LayoutItemStoryBinding.inflate(inflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ItemUiModel>() {
            override fun areItemsTheSame(oldItem: ItemUiModel, newItem: ItemUiModel): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: ItemUiModel, newItem: ItemUiModel): Boolean =
                oldItem.id == newItem.id
        }
    }
}