package security

import io.ktor.client.features.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import main.client
import main.user
import main.json
import java.security.MessageDigest

@Serializable
data class User(val _id : String = "", var username : String = "", var password : String = "", var email : String = "")

fun getMd5Digest(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray(Charsets.UTF_8))

suspend fun login() {
    println("Performing Log In:")
    print("Username: ")
    val username = readLine()
    print("Password: ")
    var password = System.console()?.readPassword() ?: readLine()

    password = getMd5Digest(password as String).toString()

    val auth = client.feature(Auth)
    if (auth?.providers?.size!! > 0)
        auth.providers.removeAt(0)
    auth.digest {
        credentials {
            DigestAuthCredentials(username = username as String, password = password)
        }
        realm = "login-user"
    }

    try {
    val response: String = client.get("http://localhost:8080/login")
        user = json.decodeFromString(response)
        println("Welcome, ${user.username}!")
    } catch (e : ClientRequestException) {
        println("Invalid username/password")
        auth.providers.removeAt(0)
        user.username = ""
        user.password = ""
    }
}

suspend fun tryToLogin() {
    var input = ""

    while (input.equals("y",true)||input.equals("n",true)) {
        println("You are not logged in, do you want to log in? [Y/N]")
        input = readLine().toString()
    }
    if (input.equals("y",true))
        login()
}

fun logout() {
    println("Logging out...")
    val auth = client.feature(Auth)
    if (auth?.providers?.size!! > 0)
        auth.providers.removeAt(0)
    user = User()
}