package com.fmyapp.storylist.presentation.storylist

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fmyapp.core.data.utils.RemoteResult
import com.fmyapp.core.domain.mapper.mapItemDomainToUiModel
import com.fmyapp.core.presentation.abstraction.BaseFragment
import com.fmyapp.core.presentation.abstraction.OnItemClickListener
import com.fmyapp.core.presentation.delegates.viewBinding
import com.fmyapp.core.presentation.model.ItemUiModel
import com.fmyapp.storylist.R
import com.fmyapp.storylist.databinding.FragmentStoryListBinding
import com.fmyapp.storylist.di.injectStoryListKoinModules
import com.fmyapp.storylist.presentation.storylist.adapter.StoryListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class StoryListFragment : BaseFragment(R.layout.fragment_story_list), OnItemClickListener {

    private val binding by viewBinding(FragmentStoryListBinding::bind)
    private val vm: StoryListViewModel by viewModel()
    private val topStoriesListId = mutableListOf<Int>()
    private val storyItemList = mutableListOf<ItemUiModel?>()
    private val arg: StoryListFragmentArgs by navArgs()

    private var adapter: StoryListAdapter? = null
    private var isGettingLastItem = false
    private var favoriteId = 0

    override fun onAttach(context: Context) {
        injectStoryListKoinModules()
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = StoryListAdapter(this)

        setupArgument()
        setupObserver()
        setupView()
    }

    private fun setupArgument() {
        favoriteId = arg.id
    }

    override fun onResume() {
        super.onResume()
        vm.getTopStories()
        if (favoriteId != 0) vm.getItemFavorite(favoriteId)
        else binding.tvMyFavoriteContent.text = ""
    }

    private fun setupObserver() {
        vm.getItemTopStories.observe(viewLifecycleOwner) { data ->
            when (data) {
                is RemoteResult.Loading -> {
                    binding.containerLoadingProgress.root.visibility = View.VISIBLE
                }
                is RemoteResult.Success -> {
                    binding.containerLoadingProgress.root.visibility = View.GONE
                    binding.cardMyFavorite.visibility = View.VISIBLE
                    topStoriesListId.addAll(data.data)

                    if (topStoriesListId.isNotEmpty()) getStoryItems(topStoriesListId)
                    else binding.containerEmptyData.root.visibility = View.VISIBLE
                }
                is RemoteResult.Error -> {
                    binding.containerLoadingProgress.root.visibility = View.GONE
                    showSnackBar(binding.root, data.errorMessage ?: "") { vm.getItemTopStories }
                }
            }
        }
        vm.getItemById.observe(viewLifecycleOwner) {
            when (it) {
                is RemoteResult.Loading -> {
                    binding.pbStoryItem.visibility = View.VISIBLE
                }
                is RemoteResult.Success -> {
                    if (isGettingLastItem) binding.pbStoryItem.visibility = View.GONE

                    storyItemList.add(mapItemDomainToUiModel.map(it.data))

                    if (storyItemList.isNotEmpty()) {
                        binding.rvStoryList.visibility = View.VISIBLE
                        binding.pbStoryItem.visibility = View.GONE
                    } else {
                        binding.containerEmptyData.root.visibility = View.VISIBLE
                        binding.rvStoryList.visibility = View.GONE
                    }

                    showStories(storyItemList)
                }
                is RemoteResult.Error -> {
                    binding.pbStoryItem.visibility = View.GONE
                    showSnackBar(binding.root, it.errorMessage ?: "", "OK")
                }
            }
        }
        vm.getItemFavorite.observe(viewLifecycleOwner) {
            when (it) {
                is RemoteResult.Loading -> {}
                is RemoteResult.Success -> {
                    binding.tvMyFavoriteContent.text = it.data.title
                }
                is RemoteResult.Error -> {}
            }
        }
    }

    private fun getStoryItems(idList: List<Int>) {
        idList.forEach {
            vm.getItemById(it)
            if (it == idList.lastIndex) isGettingLastItem = true
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showStories(storyItemList: MutableList<ItemUiModel?>) {
        adapter?.submitList(storyItemList)
        adapter?.notifyDataSetChanged()
    }

    private fun setupView() {
        binding.rvStoryList.adapter = adapter
        binding.cardMyFavorite.setOnClickListener {
            if (favoriteId != 0) {
                findNavController().navigate(
                    StoryListFragmentDirections.actionStoryListFragmentToStoryDetailFragment()
                        .setId(favoriteId)
                        .setIsFavorite(true)
                )
            } else showSnackBar(binding.root, "Belum ada data favorit", "OK")
        }
    }

    override fun onClick(id: Any) {
        findNavController().navigate(
            StoryListFragmentDirections.actionStoryListFragmentToStoryDetailFragment()
                .setId(id as Int)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}