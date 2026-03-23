package com.example.e2eapp.domain.dto

data class DashboardEmailData(
    val id: String,
    val from: String,
    val subject: String,
    val body: String,
    val profileImage: String,
    val date: String,
    val hasAttachment: Boolean
)