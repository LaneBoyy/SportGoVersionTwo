package ru.laneboy.sportgoversiontwo.data.network.wrapper

import ru.laneboy.sportgoversiontwo.data.network.wrapper.ApiException


/**
 * Ошибка, возникающая при обращении к API с ошибочным токеном доступа.
 *
 * @param link - ссылка для получения нового токена доступа.
 */
open class TokenInvalidException() : ApiException()
