package ru.laneboy.sportgoversiontwo.data.network.responses

import com.google.gson.annotations.SerializedName
import ru.laneboy.sportgoversiontwo.domain.GameData

data class GameDiagramRequest(
    @SerializedName("id")
    val id:Int,
    @SerializedName("competitionId")
    val competitionId:Int,
    @SerializedName("nodeDeep")
    val deep:Int,
    @SerializedName("competitionData")
    val gameData: GameDataRequest?,
    @SerializedName("previousTopGameId")
    val previousTopGameId:Int?,
    @SerializedName("previousBottomGameId")
    val previousBottomGameId:Int?
)