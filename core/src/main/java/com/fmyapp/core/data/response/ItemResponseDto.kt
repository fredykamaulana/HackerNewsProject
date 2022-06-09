package com.fmyapp.core.data.response

import com.squareup.moshi.Json

data class ItemResponseDto(

	@field:Json(name="by")
	val by: String? = null,

	@field:Json(name="descendants")
	val descendants: Int? = null,

	@field:Json(name="id")
	val id: Int? = null,

	@field:Json(name="kids")
	val kids: List<Int?>? = null,

	@field:Json(name="parent")
	val parent: Int? = null,

	@field:Json(name="parts")
	val parts: List<Int?>? = null,

	@field:Json(name="poll")
	val poll: Int? = null,

	@field:Json(name="score")
	val score: Int? = null,

	@field:Json(name="text")
	val text: String? = null,

	@field:Json(name="time")
	val time: Int? = null,

	@field:Json(name="title")
	val title: String? = null,

	@field:Json(name="type")
	val type: String? = null,

	@field:Json(name="url")
	val url: String? = null

)
