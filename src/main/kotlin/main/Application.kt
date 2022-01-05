package main

import chat.initChat
import io.ktor.client.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.websocket.*
import kotlinx.serialization.json.Json
import security.User
import security.login
import security.logout

val client = HttpClient {
    install(WebSockets)

    install(Auth)
}

val json = Json {
    ignoreUnknownKeys = true
    prettyPrint = true
}

var user = User()

val serverAddress = "127.0.0.1"

val serverPort = 8080

suspend fun main() {
    login()
    initChat()
    logout()
}