package cli

import picocli.CommandLine
import picocli.CommandLine.Option
import kotlin.system.exitProcess


@CommandLine.Command(
    subcommands = [
        Shutdown::class,
        CommandLine.HelpCommand::class,
        FTPSend::class,
        FTPDownload::class,
        FTPLst::class,
        Activation::class,
        Login::class,
        Logout::class,
        UserInfo::class,
        InitChat::class
    ])
class Cli : Runnable {
    override fun run() {  }
}

@CommandLine.Command(name = "shutdown", description = ["Shuts down the server"])
class Shutdown : Runnable {

    @Option(names = ["-t", "-timer"], description = ["Time before the shutdown"])
    var time : Long = 0

    override fun run() {
        Thread.sleep(time)
        exitProcess(0)
    }

}