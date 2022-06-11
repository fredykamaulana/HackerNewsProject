package com.fmyapp.storylist.domain.usecase

import com.fmyapp.core.data.utils.RemoteResult
import com.fmyapp.core.domain.model.ItemDomainModel
import com.fmyapp.storylist.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow

class ItemInteractor(private val repository: ItemRepository) : ItemUseCases {
    override suspend fun getItemTopStories(): Flow<RemoteResult<List<Int>>> =
        repository.getItemTopStories()

    override suspend fun getItemById(id: Int): Flow<RemoteResult<ItemDomainModel>> =
        repository.getItemById(id)
}