package com.fmyapp.storylist.domain.usecase

import com.fmyapp.core.data.utils.ResultState
import com.fmyapp.core.domain.mapper.mapItemDomainToUiModel
import com.fmyapp.core.domain.model.ItemDomainModel
import com.fmyapp.core.presentation.model.ItemUiModel
import com.fmyapp.storylist.domain.repository.ItemRepository
import com.fmyapp.core.domain.utils.DomainTransformer
import kotlinx.coroutines.flow.Flow

class ItemInteractor(private val repository: ItemRepository) : ItemUseCases {
    override fun getItemTopStories(): Flow<ResultState<List<Int>>> =
        object : DomainTransformer<List<Int>, List<Int>>() {
            override suspend fun fetchData(): Flow<ResultState<List<Int>>> {
                return repository.getItemTopStories()
            }

            override fun transformData(data: List<Int>?): List<Int> {
                return data ?: listOf()
            }
        }.asFlow()

    override fun getItemById(id: Int): Flow<ResultState<ItemUiModel>> =
        object : DomainTransformer<ItemDomainModel, ItemUiModel>() {
            override suspend fun fetchData(): Flow<ResultState<ItemDomainModel>> {
                return repository.getItemById(id)
            }

            override fun transformData(data: ItemDomainModel?): ItemUiModel {
                return mapItemDomainToUiModel.map(data)
            }
        }.asFlow()

}