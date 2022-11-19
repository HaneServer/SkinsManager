package tanetakumi.skinsmanager

import org.bukkit.configuration.file.FileConfiguration

class SkinsManagerConfig(plugin: SkinsManager) {
    private var plugin : SkinsManager = plugin
    private lateinit var config : FileConfiguration
    private lateinit var token : String
    private lateinit var channel : String

    init {
        load()
    }
    private fun load() {
        // 設定ファイルを保存
        plugin.saveDefaultConfig()
        if(!this::config.isInitialized){
            plugin.reloadConfig()
        }
        config = plugin.config
        token = config.getString("token").toString()
        channel = config.getString("channel-name").toString()
    }

    fun getToken(): String {
        return token
    }

    fun getChannelName(): String {
        return channel
    }
}