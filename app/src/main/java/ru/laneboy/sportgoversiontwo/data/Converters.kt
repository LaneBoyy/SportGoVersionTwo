package ru.laneboy.sportgoversiontwo.data

import ru.laneboy.sportgoversiontwo.data.network.responses.AuthDataResponse
import ru.laneboy.sportgoversiontwo.data.network.responses.GameDataRequest
import ru.laneboy.sportgoversiontwo.data.network.responses.GameDiagramRequest
import ru.laneboy.sportgoversiontwo.data.network.responses.RequestItemResponse
import ru.laneboy.sportgoversiontwo.data.network.responses.UserRole
import ru.laneboy.sportgoversiontwo.data.network.responses.UserRole.Companion.toUserRole
import ru.laneboy.sportgoversiontwo.domain.AuthData
import ru.laneboy.sportgoversiontwo.domain.GameData
import ru.laneboy.sportgoversiontwo.domain.GameDiagram
import ru.laneboy.sportgoversiontwo.domain.RequestItem
import ru.laneboy.sportgoversiontwo.domain.RequestItem.RequestStatus.Companion.toRequestStatus

fun AuthDataResponse.toAuthData() = AuthData(
    userId = userId,
    userName = userName,
    userEmail= userEmail,
    userPassword = userPassword,
    userRole = userRole.toUserRole()
)

fun RequestItemResponse.toRequestItem() = RequestItem(
    requestId = requestId,
    selectedCompetition = selectedCompetition,
    teamCaptain = teamCaptain,
    teamName = teamName,
    requestStatus = requestStatus.toRequestStatus()
)

fun GameData.toGameDataRequest() = GameDataRequest(
    firstTeam = firstTeam,
    secondTeam = secondTeam,
    firstTeamScore = firstTeamScore,
    secondTeamScore = secondTeamScore,
    gameIsEnd = gameIsEnd
)

fun GameDataRequest.toGameData() = GameData(
    firstTeam = firstTeam,
    secondTeam = secondTeam,
    firstTeamScore = firstTeamScore,
    secondTeamScore = secondTeamScore,
    gameIsEnd = gameIsEnd
)

fun GameDiagram.toRequest() = GameDiagramRequest(
    id = id,
    competitionId = competitionId,
    deep = deep,
    gameData = gameData?.toGameDataRequest(),
    previousTopGameId = previousTopGame?.id,
    previousBottomGameId = previousBottomGame?.id
)