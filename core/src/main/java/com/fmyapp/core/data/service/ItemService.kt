package com.fmyapp.core.data.service

import com.fmyapp.core.data.response.ItemResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ItemService {
    @GET("topstories.json")
    suspend fun getItemTopStories(
        @Query("print") print: String = "pretty",
        @Query("writeSizeLimit") limit: String = "small"
    ): List<Int>

    @GET("item/{id}.json")
    suspend fun getItemById(
        @Query("print") print: String = "pretty",
        @Path("id") id: Int
    ): ItemResponseDto
}