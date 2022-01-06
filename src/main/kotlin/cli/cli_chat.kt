package cli

import chat.initChat
import picocli.CommandLine

@CommandLine.Command(name = "chat", description = ["Initializes chat"])
class InitChat : Runnable {

    override fun run() {
        initChat()
    }

}