package com.fmyapp.storylist.data.repository

import com.fmyapp.core.data.mapper.mapItemDtoToDomainModel
import com.fmyapp.core.data.utils.RemoteResult
import com.fmyapp.core.domain.model.ItemDomainModel
import com.fmyapp.storylist.data.source.ItemRemoteDataSource
import com.fmyapp.storylist.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class ItemRepositoryImpl(private val remoteDataSource: ItemRemoteDataSource) : ItemRepository {
    override suspend fun getItemTopStories(): Flow<RemoteResult<List<Int>>> =
        remoteDataSource.getItemTopStories()

    override suspend fun getItemById(id: Int): Flow<RemoteResult<ItemDomainModel>> = flow {
        when (val response = remoteDataSource.getItemById(id).first()) {
            is RemoteResult.Loading -> {
                emit(RemoteResult.Loading)
            }
            is RemoteResult.Success -> {
                emit(RemoteResult.Success(mapItemDtoToDomainModel.map(response.data)))
            }
            is RemoteResult.Error -> {
                emit(RemoteResult.Error(errorMessage = response.errorMessage ?: ""))
            }
        }
    }
}