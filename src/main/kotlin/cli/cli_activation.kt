package cli

import kotlinx.coroutines.runBlocking
import picocli.CommandLine
import security.activate

@CommandLine.Command(name = "activate", description = ["Needed to activate account"])
class Activation : Runnable {

    override fun run() {
        runBlocking { activate() }
    }

}