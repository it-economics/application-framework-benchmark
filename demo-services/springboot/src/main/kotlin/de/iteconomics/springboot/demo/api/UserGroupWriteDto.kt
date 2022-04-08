package de.iteconomics.springboot.demo.api

import kotlinx.serialization.Serializable

@Serializable
internal data class UserGroupWriteDto(
    val id: Int,
    val name: String
)
