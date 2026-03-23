package com.example.e2eapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class DashboardMasterData(
    val id: String,
    val threadId: String,
    val snippet: String,
    val payload: Payload,
    val subject: String,
    val timestamp: Long,
    val isImportant: Boolean,
    val isPromotional: Boolean,
    val hasAttachments: Boolean
)
@Serializable
data class Payload(
    val from: String,
    val email: String,
    val subject: String,
    val date: String,
    val profileImage: String
)