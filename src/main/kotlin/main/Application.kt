package main

import chat.initChat
import io.ktor.client.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.websocket.*
import kotlinx.coroutines.awaitAll
import kotlinx.serialization.json.Json
import security.User
import security.login
import security.logout
import java.util.concurrent.TimeUnit

val client = HttpClient {
    install(WebSockets)

    install(Auth)
}

val json = Json {
    ignoreUnknownKeys = true
    prettyPrint = true
}

var user = User()

suspend fun main() {
    login()
    initChat()
    logout()
}