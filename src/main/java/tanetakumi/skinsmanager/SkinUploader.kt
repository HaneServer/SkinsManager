package tanetakumi.skinsmanager

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent


class SkinUploader: ListenerAdapter()
{
    override fun onMessageReceived(event: MessageReceivedEvent) {
        println("[ThreadID="+Thread.currentThread().id +"] Message(content="+event.message.contentRaw+", author="+event.message.author+")")

    }
}