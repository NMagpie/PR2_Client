package main

import cli.Cli
import io.ktor.client.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.websocket.*
import kotlinx.serialization.json.Json
import picocli.CommandLine
import security.*

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

fun main() {
    var input : Array<String>

    val cmd = CommandLine(Cli())

    println("Welcome!")

    while (true) {
        print("${user.username}>")
        input = readLine().toString().split(' ').toTypedArray()

        try {
            cmd.parseArgs(*input)
            cmd.execute(*input)
        } catch (e: CommandLine.UnmatchedArgumentException) {
            println(e.message)
        }
    }
    //listFiles("/")
/*    downloadFile("","BIPdMsb.jpeg")
    downloadFile("","test/sample.txt")*/
    //sendFile(".idea\\compiler.xml")
    /*login()
    initChat()
    logout()*/
}