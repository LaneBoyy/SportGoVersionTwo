package ru.laneboy.sportgoversiontwo.presentation.sign_up

enum class UserRole(var error: String? = null) {
    LOADING,
    PARTICIPANT,
    ORGANIZER,
    ERROR
}