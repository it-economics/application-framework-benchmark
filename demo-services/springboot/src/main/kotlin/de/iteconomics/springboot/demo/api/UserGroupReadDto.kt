package de.iteconomics.springboot.demo.api

import kotlinx.serialization.Serializable

@Serializable
internal data class UserGroupReadDto(
    val id: Int,
    val name: String
)
