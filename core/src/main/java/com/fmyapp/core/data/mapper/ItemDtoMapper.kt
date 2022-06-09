package com.fmyapp.core.data.mapper

import com.fmyapp.core.presentation.abstraction.BaseMapper
import com.fmyapp.core.data.response.ItemResponseDto
import com.fmyapp.core.domain.model.ItemDomainModel

val mapItemDtoToDomainModel = object : BaseMapper<ItemResponseDto?, ItemDomainModel>() {
    override fun map(data: ItemResponseDto?): ItemDomainModel {
        return ItemDomainModel(
            by = data?.by ?: "",
            descendants = data?.descendants ?: 0,
            id = data?.id ?: 0,
            kids = data?.kids ?: listOf(),
            parent = data?.parent ?: 0,
            parts = data?.parts ?: listOf(),
            poll = data?.poll ?: 0,
            score = data?.score ?: 0,
            text = data?.text ?: "",
            time = data?.time ?: 0,
            title = data?.title ?: "",
            type = data?.type ?: "",
            url = data?.url ?: ""
        )
    }
}