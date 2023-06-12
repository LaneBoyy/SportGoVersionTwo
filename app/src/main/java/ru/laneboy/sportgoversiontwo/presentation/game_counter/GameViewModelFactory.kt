package ru.laneboy.sportgoversiontwo.presentation.game_counter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.laneboy.sportgoversiontwo.domain.GameDiagram

class GameViewModelFactory(private val gameDiagram: GameDiagram) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameCounterViewModel::class.java)) {
            return GameCounterViewModel(gameDiagram) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}