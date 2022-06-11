package com.fmyapp.core.presentation.model

data class ItemUiModel(
    val by: String,
    val id: Int,
    val descendants: Int,
    val score: Int,
    val text: String,
    val time: Long,
    val title: String
)
