package com.fmyapp.storylist.domain.usecase

import com.fmyapp.core.data.utils.ResultState
import com.fmyapp.core.presentation.model.ItemUiModel
import kotlinx.coroutines.flow.Flow

interface ItemUseCases {
    fun getItemTopStories(): Flow<ResultState<List<Int>>>

    fun getItemById(id: Int): Flow<ResultState<ItemUiModel>>
}