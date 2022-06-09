package com.fmyapp.storylist.presentation.storylist

import android.content.Context
import android.os.Bundle
import android.view.View
import com.fmyapp.core.data.utils.ResultState
import com.fmyapp.core.presentation.abstraction.BaseFragment
import com.fmyapp.core.presentation.abstraction.OnItemClickListener
import com.fmyapp.core.presentation.delegates.viewBinding
import com.fmyapp.core.presentation.model.ItemUiModel
import com.fmyapp.storylist.R
import com.fmyapp.storylist.databinding.FragmentStoryListBinding
import com.fmyapp.storylist.di.injectStoryLIstKoinModules
import com.fmyapp.storylist.presentation.storylist.adapter.StoryListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class StoryListFragment : BaseFragment(R.layout.fragment_story_list), OnItemClickListener {

    private val binding by viewBinding(FragmentStoryListBinding::bind)
    private val vm: StoryListViewModel by viewModel()
    private val topStoriesListId = mutableListOf<Int>()
    private val storyItemList = mutableListOf<ItemUiModel?>()

    private var adapter: StoryListAdapter? = null
    private var isGettingLastItem = false
    private var favoriteId = 0

    override fun onAttach(context: Context) {
        injectStoryLIstKoinModules()
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        setupView()

        adapter = StoryListAdapter(this)
    }

    override fun onResume() {
        super.onResume()
        vm.getItemTopStories
    }

    private fun setupObserver() {
        vm.getItemTopStories.observe(viewLifecycleOwner) {
            when (it) {
                is ResultState.Loading -> {
                    binding.containerLoadingProgress.root.visibility = View.VISIBLE
                }
                is ResultState.Success -> {
                    binding.containerLoadingProgress.root.visibility = View.GONE
                    binding.cardMyFavorite.visibility = View.VISIBLE
                    topStoriesListId.addAll(it.data ?: listOf())

                    if (topStoriesListId.isNotEmpty()) getStoryItems(topStoriesListId)
                    else binding.containerEmptyData.root.visibility = View.VISIBLE
                }
                is ResultState.Error -> {
                    binding.containerLoadingProgress.root.visibility = View.GONE
                    showSnackBar(binding.root, it.message ?: "") { vm.getItemTopStories }
                }
            }
        }
        vm.getItemById.observe(viewLifecycleOwner) {
            when (it) {
                is ResultState.Loading -> {
                    binding.pbStoryItem.visibility = View.VISIBLE
                }
                is ResultState.Success -> {
                    if (isGettingLastItem) binding.pbStoryItem.visibility = View.GONE
                    storyItemList.add(it.data)
                }
                is ResultState.Error -> {
                    binding.pbStoryItem.visibility = View.GONE
                    showSnackBar(binding.root, it.message ?: "", "OK")
                }
            }
        }
    }

    private fun getStoryItems(idList: List<Int>) {
        idList.forEach {
            vm.getItemById(it)
            if (it == idList.lastIndex) isGettingLastItem = true
        }

        if (storyItemList.isNotEmpty()) {
            adapter?.submitList(storyItemList)
            binding.rvStoryList.visibility = View.VISIBLE
        } else {
            binding.containerEmptyData.root.visibility = View.VISIBLE
            binding.rvStoryList.visibility = View.GONE
        }
    }

    private fun setupView() {
        binding.rvStoryList.adapter = adapter
    }

    override fun onClick(id: Any) {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}