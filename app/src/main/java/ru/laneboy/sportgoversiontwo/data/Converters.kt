package ru.laneboy.sportgoversiontwo.data

import ru.laneboy.sportgoversiontwo.data.network.responses.AuthDataResponse
import ru.laneboy.sportgoversiontwo.data.network.responses.RequestItemResponse
import ru.laneboy.sportgoversiontwo.data.network.responses.UserRole
import ru.laneboy.sportgoversiontwo.data.network.responses.UserRole.Companion.toUserRole
import ru.laneboy.sportgoversiontwo.domain.AuthData
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