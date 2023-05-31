package ru.laneboy.sportgoversiontwo.domain

import ru.laneboy.sportgoversiontwo.data.network.responses.UserRole

data class AuthData(
    val userId: Int,
    val userName: String?,
    val userEmail: String,
    val userPassword: String,
    val userRole: UserRole
)
