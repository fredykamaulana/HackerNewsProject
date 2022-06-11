package com.fmyapp.storylist.presentation.storydetail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fmyapp.core.presentation.model.ItemUiModel
import com.fmyapp.storylist.databinding.LayoutItemCommentBinding
import com.fmyapp.storylist.presentation.utils.readHtml

class StoryDetailAdapter() :
    ListAdapter<ItemUiModel, StoryDetailAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.create(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: LayoutItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: ItemUiModel) {
            binding.tvCommentItemTitle.text = item.text.readHtml()
            binding.tvStoryItemCommentsUser.text = item.by
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = LayoutItemCommentBinding.inflate(inflater, parent, false)

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