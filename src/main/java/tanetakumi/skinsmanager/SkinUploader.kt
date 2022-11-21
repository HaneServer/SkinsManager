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
import java.io.File
import java.io.IOException
import java.nio.charset.Charset


class SkinUploader: ListenerAdapter()
{
    var channelName : String= "skin-upload"
    init{
        println("init")
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        val mes = event.message.contentRaw
        val args = mes.split("\\s+".toRegex())
        val channel = event.channel
        val user = event.message.author.name
        val helpMessage = "SkinsManagerの使い方\n例: `!skin <名前> <classic か slim>` \n同時にスキンの画像を張ってください。"

        if (channel.name == channelName) {
            if (args[0] == "!skin") {
                println("[ThreadID=" + Thread.currentThread().id + "] Message(content=" + mes + ", author=" + user + ", channel=" + channel.name + ")")
                if (args.size == 3) {
                    val name = args[1]
                    val style = args[2]
                    if ((style == "classic" || style == "slim")
                        && name.matches(Regex("^[a-z]+\$"))
                        && event.message.attachments.size == 1
                    ) {

                        val client: OkHttpClient = OkHttpClient.Builder().build()
                        // create json
                        val json = JSONObject()
                        json["variant"] = style
                        json["name"] = name
                        json["visibility"] = 0
                        json["url"] = event.message.attachments[0].url

                        println(event.message.attachments[0])
                        val postBody = json.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
                        val request = Request.Builder()
                            .url("https://api.mineskin.org/generate/url")
                            .post(postBody)
                            .build()

                        client.newCall(request).enqueue(object : Callback {
                            override fun onFailure(call: Call, e: IOException) {
                                val errorMes = "エラーが発生しました。\nアップロードするファイルに問題はありませんか？\nエラーの詳細\n" + e.message
                                channel.sendMessage(errorMes).queue()
                            }

                            override fun onResponse(call: Call, response: Response) {
                                val content = response.body?.source()?.readUtf8()
                                println(content)
                                if (content != null) {
                                    File("out.txt").writeText(content, Charset.defaultCharset())
                                } else {
                                    println("null")
                                }
                            }
                        })

                    } else {
                        channel.sendMessage(helpMessage).queue()
                    }
                } else {
                    channel.sendMessage(helpMessage).queue()
                }
            }
        }
    }
}