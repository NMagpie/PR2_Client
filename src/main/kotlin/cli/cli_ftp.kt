package cli

import ftp.downloadFile
import ftp.listFiles
import ftp.sendFile
import picocli.CommandLine

@CommandLine.Command(name = "ftp-send", description = ["Sends file to the FTP server"])
class FTPSend : Runnable {

    @CommandLine.Parameters(index = "0", description = ["Path of file which have to be sent"])
    private var path: StringBuilder? = null

    override fun run() {
        if (path!=null)
        sendFile(path.toString())
    }

}

@CommandLine.Command(name = "ftp-get", description = ["Downloads file to the FTP server"])
class FTPDownload : Runnable {

    @CommandLine.Parameters(index = "0", description = ["Path of destination in local device"])
    private val pathArg: StringBuilder? = null

    @CommandLine.Parameters(index = "1", description = ["Path of file which have to be downloaded"])
    private val remotePathArg: StringBuilder? = null

    override fun run() {
        if (pathArg!=null && remotePathArg!=null)
            downloadFile(pathArg.toString(), remotePathArg.toString())
    }

}

@CommandLine.Command(name = "ftp-list", description = ["Lists all the files in current path"])
class FTPLst : Runnable {

    @CommandLine.Parameters(index = "0", description = ["Path of directory to be listed"])
    private val path: StringBuilder? = null

    override fun run() {
        if (path!=null)
            listFiles(path.toString())
    }

}