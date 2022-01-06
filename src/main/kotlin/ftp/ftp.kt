package ftp

import de.m3y.kformat.Table
import de.m3y.kformat.table
import main.serverAddress
import main.user
import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPReply
import security.User
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

var defaultDirectory: String = System.getProperty("user.dir")

val ftpClient = FTPClient()

fun initFtpConnection() {
    //ftpClient.addProtocolCommandListener(PrintCommandListener(PrintWriter(System.out)))

    ftpClient.connect(serverAddress, 21)

    val reply = ftpClient.replyCode

    if (!FTPReply.isPositiveCompletion(reply)) {
        ftpClient.disconnect()
        throw IOException("e")
    }

    if (user.activated) {
        if (!ftpClient.login("test", "test123"))
            ftpClient.disconnect()
    } else {
        if (user != User()) {
            println("Your account is not activated! You will be able only to download files from the server!")

            if (!ftpClient.login("user", "user"))
                ftpClient.disconnect()
        } else
            println("You are not logged in! You don't have access to the shared files!")
    }
}

fun closeFtpConnection() {
    ftpClient.disconnect()
}

fun sendFile(pathArg: String) {
    if (!checkConnection()) return
    if (!user.activated) {
        println("You have no permission for this action. Activate you account to send files.")
        return
    }
    var path = if (!File(pathArg).isAbsolute) "$defaultDirectory\\$pathArg" else pathArg

    path = path.replace('\\', '/')

    if (!(File(path).exists() && File(path).isFile)) {
        println("Error: current path does not exist or is not a file!")
        return
    }

    val fileName = path.split("/".toRegex()).last()

    ftpClient.storeFile(fileName, FileInputStream(File(path)))

    println("File $fileName was successfully sent!")
}

fun downloadFile(pathArg: String, remotePathArg: String) {
    if (!checkConnection()) return
    var path = if (!File(pathArg).isAbsolute) "$defaultDirectory\\$pathArg" else pathArg
    var remotePath = remotePathArg

    path = path.replace('\\', '/')
    remotePath = remotePath.replace('\\', '/')

    if (!(File(path).exists() && File(path).isDirectory)) {
        println("Error: current path does not exist or is not a file!")
        return
    }
    val fileName = remotePath.split("/".toRegex()).last()

    if (!path.endsWith('/'))
        path += "/"

    if (!remotePath.startsWith('/'))
        remotePath = "/$remotePath"

    val fos = FileOutputStream(path + fileName)

    ftpClient.retrieveFile(remotePath, fos)

    println("File $fileName was successfully downloaded!\nYou can open it: $path$fileName")
}

fun listFiles(pathArg: String) {
    if (!checkConnection()) return
    val path = pathArg.replace('\\', '/')
    val files = ftpClient.listFiles(path)
    val listedFiles = files.asList()
    println("Files in \'$path\':")
    val fileTable = table {
        header("Type", "Name", "Size (KB)", "Time")

        for (file in listedFiles) {
            row(
                if (file.isDirectory) "D" else "F",
                file.name,
                (file.size.toFloat() / 1_000),
                file.timestamp.time.toString()
            )
        }

        hints {
            alignment(0, Table.Hints.Alignment.RIGHT)
            alignment(1, Table.Hints.Alignment.LEFT)
            alignment(2, Table.Hints.Alignment.RIGHT)
            alignment(3, Table.Hints.Alignment.LEFT)
            borderStyle = Table.BorderStyle.SINGLE_LINE
            precision("Size (KB)", 3)
        }
    }.render()
    println(fileTable)
}

fun checkConnection(): Boolean {
    if (!(ftpClient.isConnected && ftpClient.isAvailable))
        initFtpConnection()

    return if (ftpClient.isConnected && ftpClient.isAvailable)
        true
    else {
        println("The FTP server is not available! Check connection or try again later!")
        false
    }
}