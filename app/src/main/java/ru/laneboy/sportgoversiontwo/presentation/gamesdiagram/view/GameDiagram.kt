package ru.laneboy.sportgoversiontwo.presentation.gamesdiagram.view

data class GameDiagram(
    var firstTeam: String? = null,
    var secondTeam: String? = null,
    var winnerTeam:String?= null,
    var previousTopMatch: GameDiagram?,
    var previousBottomMatch: GameDiagram?
)