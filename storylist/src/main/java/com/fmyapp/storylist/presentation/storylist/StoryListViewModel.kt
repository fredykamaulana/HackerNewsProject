package com.fmyapp.storylist.presentation.storylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fmyapp.core.data.utils.RemoteResult
import com.fmyapp.core.domain.model.ItemDomainModel
import com.fmyapp.storylist.domain.usecase.ItemUseCases
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

class StoryListViewModel(private val useCases: ItemUseCases) : ViewModel() {
    private val _getItemTopStories = MutableLiveData<RemoteResult<List<Int>>>()
    val getItemTopStories: LiveData<RemoteResult<List<Int>>> get() = _getItemTopStories
    fun getTopStories() {
        _getItemTopStories.value = RemoteResult.Loading
        viewModelScope.launch {
            _getItemTopStories.value = useCases.getItemTopStories().last()
        }
    }

    private val _getItemById = MutableLiveData<RemoteResult<ItemDomainModel>>()
    val getItemById: LiveData<RemoteResult<ItemDomainModel>> get() = _getItemById
    fun getItemById(id: Int) {
        _getItemById.value = RemoteResult.Loading
        viewModelScope.launch {
            _getItemById.value = useCases.getItemById(id).last()
        }
    }

    private val _getItemFavorite = MutableLiveData<RemoteResult<ItemDomainModel>>()
    val getItemFavorite: LiveData<RemoteResult<ItemDomainModel>> get() = _getItemFavorite
    fun getItemFavorite(id: Int) {
        _getItemFavorite.value = RemoteResult.Loading
        viewModelScope.launch {
            _getItemFavorite.value = useCases.getItemById(id).last()
        }
    }
}