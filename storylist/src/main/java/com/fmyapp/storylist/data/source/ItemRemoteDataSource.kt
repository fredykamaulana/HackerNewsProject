package com.fmyapp.storylist.data.source

import com.fmyapp.core.data.response.ItemResponseDto
import com.fmyapp.core.data.service.ItemService
import com.fmyapp.core.data.utils.RemoteResult
import com.fmyapp.core.data.utils.SafeApiCall
import kotlinx.coroutines.flow.Flow

class ItemRemoteDataSource(private val apiService: ItemService) : SafeApiCall() {
    suspend fun getItemTopStories(): Flow<RemoteResult<List<Int>>> {
        return safeApiCall { setTopSize(16, apiService.getItemTopStories()) }
    }

    private fun setTopSize(size: Int, input: List<Int>): List<Int> {
        val sizedList = mutableListOf<Int>()
        for (i in 0..size) {
            sizedList.add(input[i])
        }

        return sizedList
    }

    suspend fun getItemById(id: Int): Flow<RemoteResult<ItemResponseDto>> {
        return safeApiCall { apiService.getItemById(id = id) }
    }
}