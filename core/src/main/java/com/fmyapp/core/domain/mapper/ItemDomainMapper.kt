package com.fmyapp.core.domain.mapper

import com.fmyapp.core.presentation.abstraction.BaseMapper
import com.fmyapp.core.domain.model.ItemDomainModel
import com.fmyapp.core.presentation.model.ItemUiModel

val mapItemDomainToUiModel = object : BaseMapper<ItemDomainModel?, ItemUiModel>() {
    override fun map(data: ItemDomainModel?): ItemUiModel {
        return ItemUiModel(
            id = data?.id ?: 0,
            descendants = data?.descendants ?: 0,
            score = data?.score ?: 0,
            title = data?.title ?: ""
        )
    }
}