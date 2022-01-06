package security

import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import main.client
import main.serverAddress
import main.serverPort
import main.user

suspend fun activate() {
    if (user == User()) {
        println("Log in first")
        return
    }
    if (user.activated) {
        println("User\'s account has already been activated")
        return
    }
    val response: String = client.get("http://$serverAddress:$serverPort/activate")
    if (response == "okay") {
        println("On your email was sent activation code. Type it below to activate your account.")
        print("Write your code: ")
        val code = readLine()
            val response: String = client.get("http://$serverAddress:$serverPort/activate/code") {
                parameter("code", code)
            }
        if (response == "okay") {
            user.activated = true
            println("Your account was successfully activated!")
        }
        else
            println("The activation code is incorrect.\nPlease, try again")
    }
}