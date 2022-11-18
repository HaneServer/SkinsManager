package tanetakumi.skinsmanager

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import org.bukkit.plugin.java.JavaPlugin


class SkinsManager : JavaPlugin() {
    private var jda :JDA? = null
    private lateinit var skinsManagerConfig: SkinsManagerConfig

    override fun onEnable() {
        skinsManagerConfig = SkinsManagerConfig(this);
        val token = skinsManagerConfig.getToken()
        if(token == ""){
            println("SkinsManager is disabled due to the empty token")
        } else {
            jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT) // enables explicit access to message.getContentDisplay()
                .addEventListeners(SkinUploader())
                .build()
        }
    }
    override fun onDisable() {
        // Plugin shutdown logic
        jda?.shutdownNow()
    }
}
