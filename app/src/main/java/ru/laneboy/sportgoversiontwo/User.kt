package ru.laneboy.sportgoversiontwo

data class User(
    val userId: Int,
    var userName: String,
    val userEmail: String,
    val userPassword: String,
    val userRole: String,
)