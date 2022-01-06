package security

import ftp.ftpClient
import io.ktor.client.features.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import main.*
import java.security.MessageDigest

@Serializable
data class User(val _id : String = "", var username : String = "", var password : String = "", var email : String = "", var activated: Boolean = false)

fun getMd5Digest(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray(Charsets.UTF_8))

fun login() {
    println("Performing Log In:")
    print("Username: ")
    val username = readLine() as String
    print("Password: ")
    var password = System.console()?.readPassword() ?: readLine() as String

    password = String(getMd5Digest(password as String))

    val auth = client.feature(Auth)
    if (auth?.providers?.size!! > 0)
        auth.providers.removeAt(0)
    auth.digest {
        credentials {
                DigestAuthCredentials(username = username, password = password)
        }
        realm = "login-user"
    }

/*    println("$username:login-user:$password")
    println(getMd5Digest("$username:login-user:$password"))*/

    try {
        runBlocking {
            val response: String = client.get("http://${serverAddress}:${serverPort}/login")
            user = json.decodeFromString(response)
            println("Welcome, ${user.username}!")
        }
    } catch (e : ClientRequestException) {
        println("Invalid username/password")
        e.printStackTrace()
        auth.providers.removeAt(0)
        user = User()
    }
}

fun tryToLogin() {
    var input = ""

    while (input.equals("y",true)||input.equals("n",true)) {
        println("You are not logged in, do you want to log in? [Y/N]")
        input = readLine().toString()
    }
    if (input.equals("y",true))
        runBlocking { login() }
}

fun logout() {
    println("Logging out...")
    val auth = client.feature(Auth)
    if (auth?.providers?.size!! > 0)
        auth.providers.removeAt(0)
    user = User()
    ftpClient.logout()
}

fun userInfo() {
    if (user == User()) {
        println("You are not logged in!")
        return
    }

    println("Username: ${user.username}\n" +
            "Email: ${user.email}\n" +
            "Activated: "+ if (user.activated) "Yes" else "No")

}