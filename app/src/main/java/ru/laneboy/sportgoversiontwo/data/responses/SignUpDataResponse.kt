package ru.laneboy.sportgoversiontwo.data.responses

data class SignUpDataResponse(
    val email: String,
    val name: String,
    val password: String,
    val userRole: Int
)