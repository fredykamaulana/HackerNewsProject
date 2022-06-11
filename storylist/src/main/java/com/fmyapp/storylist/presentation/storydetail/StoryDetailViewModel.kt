package com.fmyapp.storylist.presentation.storydetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fmyapp.core.data.utils.RemoteResult
import com.fmyapp.core.domain.model.ItemDomainModel
import com.fmyapp.storylist.domain.usecase.ItemUseCases
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

class StoryDetailViewModel(private val useCases: ItemUseCases) : ViewModel() {
    private val _getItemById = MutableLiveData<RemoteResult<ItemDomainModel>>()
    val getItemById: LiveData<RemoteResult<ItemDomainModel>> get() = _getItemById
    fun getItemById(id: Int) {
        _getItemById.value = RemoteResult.Loading
        viewModelScope.launch {
            _getItemById.value = useCases.getItemById(id).last()
        }
    }

    private val _getItemComment = MutableLiveData<RemoteResult<ItemDomainModel>>()
    val getItemComment: LiveData<RemoteResult<ItemDomainModel>> get() = _getItemComment
    fun getItemComment(id: Int) {
        _getItemComment.value = RemoteResult.Loading
        viewModelScope.launch {
            _getItemComment.value = useCases.getItemById(id).last()
        }
    }
}