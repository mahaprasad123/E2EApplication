package com.example.e2eapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserAvtarData(
    val data: UserDP,
)

@Serializable
data class UserDP(
    val avatar: String,
)
