package cli

import picocli.CommandLine
import security.login
import security.logout
import security.userInfo

@CommandLine.Command(name = "login", description = ["Used to log into the system"])
class Login : Runnable {

    override fun run() {
        login()
    }

}

@CommandLine.Command(name = "logout", description = ["Used to log out of the system"])
class Logout : Runnable {

    override fun run() {
        logout()
    }

}

@CommandLine.Command(name = "user", description = ["Prints info about user"])
class UserInfo : Runnable {

    override fun run() {
        userInfo()
    }

}