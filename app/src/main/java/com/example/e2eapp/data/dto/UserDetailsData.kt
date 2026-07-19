package com.example.e2eapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDetailsData(
    var code: String? = null,
    var name: String? = null,
    var areaSqKm: Int? = null,
    var population: Long? = null,
    var lines: ArrayList<String> = arrayListOf(),
    var countries: Int? = null,
    var oceans: ArrayList<String> = arrayListOf(),
    var developedCountries: ArrayList<String> = arrayListOf(),
)
