package tanetakumi.skinsmanager

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Invite
import net.dv8tion.jda.api.requests.GatewayIntent

class HaneBot {
    private lateinit var jda: JDA

    init{
        jda = JDABuilder.createDefault(token) // messageの取得を許可
            .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_VOICE_STATES) // GatewayIntent.GUILD_PRESENCES,
            .addEventListeners(commandClient) // Regi commandClient
            .addEventListeners(AddXP()) // add jda listener
            .build()
    }

}