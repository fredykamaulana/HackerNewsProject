package com.fmyapp.storylist.domain.repository

import com.fmyapp.core.data.utils.ResultState
import com.fmyapp.core.domain.model.ItemDomainModel
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun getItemTopStories(): Flow<ResultState<List<Int>>>

    fun getItemById(id: Int): Flow<ResultState<ItemDomainModel>>
}