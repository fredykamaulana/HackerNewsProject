package com.fmyapp.storylist.domain.repository

import com.fmyapp.core.data.utils.RemoteResult
import com.fmyapp.core.domain.model.ItemDomainModel
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    suspend fun getItemTopStories(): Flow<RemoteResult<List<Int>>>

    suspend fun getItemById(id: Int): Flow<RemoteResult<ItemDomainModel>>
}