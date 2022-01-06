package main

import chat.initChat
import ftp.downloadFile
import ftp.initFtpConnection
import ftp.listFiles
import ftp.sendFile
import io.ktor.client.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.websocket.*
import kotlinx.serialization.json.Json
import security.User
import security.login
import security.logout
import java.net.InetAddress

val client = HttpClient {
    install(WebSockets)

    install(Auth)
}

val json = Json {
    ignoreUnknownKeys = true
    prettyPrint = true
}

var user = User()

val serverAddress = "192.168.100.16"

val serverPort = 8080

suspend fun main() {
    //listFiles("/")
/*    downloadFile("","BIPdMsb.jpeg")
    downloadFile("","test/sample.txt")*/
    //sendFile(".idea\\compiler.xml")
    /*login()
    initChat()
    logout()*/
}