package com.fmyapp.storylist.data.repository

import com.fmyapp.core.data.mapper.mapItemDtoToDomainModel
import com.fmyapp.core.data.response.ItemResponseDto
import com.fmyapp.core.data.utils.DataTransformer
import com.fmyapp.core.data.utils.RemoteResult
import com.fmyapp.core.data.utils.ResultState
import com.fmyapp.core.domain.model.ItemDomainModel
import com.fmyapp.storylist.data.source.ItemRemoteDataSource
import com.fmyapp.storylist.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow

class ItemRepositoryImpl(private val remoteDataSource: ItemRemoteDataSource) : ItemRepository {
    override fun getItemTopStories(): Flow<ResultState<List<Int>>> =
        object : DataTransformer<List<Int>, List<Int>>() {
            override suspend fun fetchData(): Flow<RemoteResult<List<Int>>> {
                return remoteDataSource.getItemTopStories()
            }

            override fun transformData(data: List<Int>?): List<Int> {
                return data ?: listOf()
            }
        }.asFlow()

    override fun getItemById(id: Int): Flow<ResultState<ItemDomainModel>> =
        object : DataTransformer<ItemResponseDto, ItemDomainModel>() {
            override suspend fun fetchData(): Flow<RemoteResult<ItemResponseDto>> {
                return remoteDataSource.getItemById(id)
            }

            override fun transformData(data: ItemResponseDto?): ItemDomainModel {
                return mapItemDtoToDomainModel.map(data)
            }
        }.asFlow()

}