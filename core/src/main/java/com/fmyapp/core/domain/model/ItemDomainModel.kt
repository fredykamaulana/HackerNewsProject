package com.fmyapp.core.domain.model

data class ItemDomainModel(
    val by: String,
    val descendants: Int,
    val id: Int,
    val kids: List<Int?>,
    val parent: Int,
    val parts: List<Int?>,
    val poll: Int,
    val score: Int,
    val text: String,
    val time: Int,
    val title: String,
    val type: String,
    val url: String
)
