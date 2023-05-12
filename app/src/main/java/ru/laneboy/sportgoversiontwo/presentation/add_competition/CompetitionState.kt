package ru.laneboy.sportgoversiontwo.presentation.add_competition

enum class CompetitionState(var error: String? = null) {
    LOADING, SUCCESS, ERROR
}