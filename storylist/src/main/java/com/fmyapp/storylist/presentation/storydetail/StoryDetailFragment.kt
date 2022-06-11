package com.fmyapp.storylist.presentation.storydetail

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fmyapp.core.data.utils.RemoteResult
import com.fmyapp.core.domain.mapper.mapItemDomainToUiModel
import com.fmyapp.core.presentation.abstraction.BaseFragment
import com.fmyapp.core.presentation.delegates.viewBinding
import com.fmyapp.core.presentation.model.ItemUiModel
import com.fmyapp.storylist.R
import com.fmyapp.storylist.databinding.FragmentStoryDetailBinding
import com.fmyapp.storylist.di.injectStoryListKoinModules
import com.fmyapp.storylist.presentation.storydetail.adapter.StoryDetailAdapter
import com.fmyapp.storylist.presentation.utils.convertEpochSecond
import com.fmyapp.storylist.presentation.utils.readHtml
import org.koin.androidx.viewmodel.ext.android.viewModel

class StoryDetailFragment : BaseFragment(R.layout.fragment_story_detail) {

    private val binding by viewBinding(FragmentStoryDetailBinding::bind)
    private val vm: StoryDetailViewModel by viewModel()
    private val itemChildListId = mutableListOf<Int>()
    private val commentItemList = mutableListOf<ItemUiModel?>()
    private val arg: StoryDetailFragmentArgs by navArgs()

    private var adapter: StoryDetailAdapter? = null
    private var isGettingLastItem = false
    private var isFavourite = false
    private var itemId = 0

    override fun onAttach(context: Context) {
        injectStoryListKoinModules()
        setupOnBackPressed()
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = StoryDetailAdapter()

        setupArgument()
        setupObserver()
        setupView()
    }

    private fun setupArgument() {
        itemId = arg.id
        isFavourite = arg.isFavorite
    }

    private fun setupObserver() {
        vm.getItemById.observe(viewLifecycleOwner) { data ->
            when (data) {
                is RemoteResult.Loading -> {
                    binding.containerLoadingProgress.root.visibility = View.VISIBLE
                }
                is RemoteResult.Success -> {
                    binding.containerLoadingProgress.root.visibility = View.GONE

                    data.data.kids.forEachIndexed { index, it ->
                        if (index <= 21) itemChildListId.add(it ?: 0)
                    }

                    bindData(mapItemDomainToUiModel.map(data.data))

                    if (itemChildListId.isNotEmpty()) getCommentItems(itemChildListId)
                }
                is RemoteResult.Error -> {
                    binding.containerLoadingProgress.root.visibility = View.GONE
                    showSnackBar(binding.root, data.errorMessage ?: "", "OK")
                }
            }
        }
        vm.getItemComment.observe(viewLifecycleOwner) { data ->
            when (data) {
                is RemoteResult.Loading -> {
                    binding.pbStoryItem.visibility = View.VISIBLE
                }
                is RemoteResult.Success -> {
                    if (isGettingLastItem) binding.pbStoryItem.visibility = View.GONE

                    commentItemList.add(mapItemDomainToUiModel.map(data.data))

                    if (commentItemList.isNotEmpty()) {
                        binding.rvStoryComment.visibility = View.VISIBLE
                        binding.pbStoryItem.visibility = View.GONE
                    } else {
                        binding.rvStoryComment.visibility = View.GONE
                    }

                    showStories(commentItemList)
                }
                is RemoteResult.Error -> {
                    binding.pbStoryItem.visibility = View.GONE
                    showSnackBar(binding.root, data.errorMessage ?: "", "OK")
                }
            }
        }
    }

    private fun bindData(data: ItemUiModel) {
        binding.tvDetailStoryTitle.text = data.title
        binding.tvDetailStoryUser.text = data.by
        binding.tvDetailStoryDate.text = data.time.convertEpochSecond()
        binding.tvDetailStoryContent.text = data.text.readHtml()
    }

    private fun getCommentItems(idList: List<Int>) {
        idList.forEach {
            vm.getItemComment(it)
            if (it == idList.lastIndex) isGettingLastItem = true
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showStories(commentItemList: MutableList<ItemUiModel?>) {
        adapter?.submitList(commentItemList)
        adapter?.notifyDataSetChanged()
    }

    private fun setupView() {
        binding.rvStoryComment.adapter = adapter
        binding.ivFavorite.setOnClickListener {
            if (isFavourite) {
                isFavourite = false
                binding.ivFavorite.setImageResource(R.drawable.ic_star_border)
                showSnackBar(binding.root, "Favorit story ini sudah dihapus", "OK")
            } else {
                isFavourite = true
                binding.ivFavorite.setImageResource(R.drawable.ic_star_filled)
                showSnackBar(binding.root, "Story ini ditandai sebagai favorit", "OK")
            }
        }

        if (isFavourite) binding.ivFavorite.setImageResource(R.drawable.ic_star_filled)
        else binding.ivFavorite.setImageResource(R.drawable.ic_star_border)
    }

    override fun onResume() {
        super.onResume()
        vm.getItemById(itemId)
    }

    private fun setupOnBackPressed() {
        val onBackPressed = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentFragment = findNavController().currentDestination
                when (currentFragment?.id) {
                    R.id.storyDetailFragment -> {
                        if (isFavourite) findNavController().navigate(
                            StoryDetailFragmentDirections.actionStoryDetailFragmentToStoryListFragment()
                                .setId(itemId)
                                .setIsFavorite(true)
                        )
                        else findNavController().navigate(
                            StoryDetailFragmentDirections.actionStoryDetailFragmentToStoryListFragment()
                                .setId(0)
                                .setIsFavorite(false)
                        )
                    }
                    else -> findNavController().navigateUp()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressed)
    }
}