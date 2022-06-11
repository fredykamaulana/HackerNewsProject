package com.fmyapp.storylist.domain.usecase

import com.fmyapp.core.data.utils.RemoteResult
import com.fmyapp.core.domain.model.ItemDomainModel
import kotlinx.coroutines.flow.Flow

interface ItemUseCases {
    suspend fun getItemTopStories(): Flow<RemoteResult<List<Int>>>

    suspend fun getItemById(id: Int): Flow<RemoteResult<ItemDomainModel>>
}