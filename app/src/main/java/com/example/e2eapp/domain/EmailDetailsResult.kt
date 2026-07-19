package com.example.e2eapp.domain

import com.example.e2eapp.domain.dto.EmailDetailsData

sealed interface EmailDetailsResult {
    data class UserDetails(
        var emailDetailsData: EmailDetailsData,
    ) : EmailDetailsResult

    data class UserDetailsError(
        var msg: String,
    ) : EmailDetailsResult
}
