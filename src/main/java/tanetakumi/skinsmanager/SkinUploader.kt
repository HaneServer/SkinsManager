package tanetakumi.skinsmanager

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.simple.JSONObject
import java.io.IOException


class SkinUploader: ListenerAdapter()
{
    override fun onMessageReceived(event: MessageReceivedEvent) {
        val mes = event.message.contentRaw
        val args = mes.split("\\s+")
        val channel = event.channel
        val user = event.message.author.name
        val helpMessage = "SkinsManagerの使い方\n例: `!skin <名前> <classic か slim>` \n同時にスキンの画像を張ってください。"

        if(channel.name == "skin-upload"){
            if(args[0] == "!skin"){
                println("[ThreadID="+Thread.currentThread().id +"] Message(content="+mes+", author="+user+", channel="+user+")")
                if(args.size == 3) {
                    val name = args[1]
                    val style = args[2]
                    if((style == "classic" || style == "slim")
                        && name.matches(Regex("^[a-z]+\$"))
                        && event.message.attachments.size == 1){

                        val response = post(args[0], event.message.attachments[0].url, args[1])
                        println(response.toString())

                    } else {
                        channel.sendMessage(helpMessage)
                    }
                } else {
                    channel.sendMessage(helpMessage)
                }
            }
        }
    }

    @Throws(IOException::class)
    fun post(name: String, imageURL: String, variant: String): Response {

        val client: OkHttpClient = OkHttpClient.Builder().build()
        // create json
        val json = JSONObject()
        json["variant"] = variant
        json["name"] = name
        json["visibility"] = 0
        json["url"] = imageURL

        val postBody = json.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
        val request = Request.Builder()
            .url("https://api.mineskin.org/generate/url")
            .post(postBody)
            .build()

        return client.newCall(request).execute()
        //client.newCall(request).execute().use { response -> return response.body()?.string() }
    }
}