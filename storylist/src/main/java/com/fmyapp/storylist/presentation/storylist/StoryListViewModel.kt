package com.fmyapp.storylist.presentation.storylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fmyapp.core.data.utils.ResultState
import com.fmyapp.core.presentation.model.ItemUiModel
import com.fmyapp.storylist.domain.usecase.ItemUseCases
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

class StoryListViewModel(private val useCase: ItemUseCases) : ViewModel() {
    val getItemTopStories = useCase.getItemTopStories().asLiveData()

    private val _getItemById = MutableLiveData<ResultState<ItemUiModel>>()
    val getItemById: LiveData<ResultState<ItemUiModel>> get() = _getItemById
    fun getItemById(id: Int) {
        _getItemById.value = ResultState.Loading()
        viewModelScope.launch {
            _getItemById.value = useCase.getItemById(id).last()
        }
    }
}