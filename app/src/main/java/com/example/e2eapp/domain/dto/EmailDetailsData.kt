package com.example.e2eapp.domain.dto

data class EmailDetailsData(
    val name: String,
    val continentName: String,
    val developedCountries: List<String>,
    val message: String,
)
