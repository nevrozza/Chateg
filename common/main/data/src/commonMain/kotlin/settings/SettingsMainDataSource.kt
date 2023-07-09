package settings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set

class SettingsMainDataSource(
    private val settings: Settings
) {
    fun saveMessages(messages: String) {
        settings[messagesKey] = messages
    }



    fun String.toMap(): Map<String, String> {
        val trimmedString = this.trim('{', '}')
        val mapTexts = mutableMapOf<String, String>()
        val textPairs = trimmedString.split(", ")

        for (textPair in textPairs) {
            val keyValue = textPair.split("=")
            if(keyValue.size == 2) {
                val key = keyValue[0].trim()
                val value = keyValue[1].trim()
                mapTexts[key] = value
            }
        }
        return mapTexts
    }

    fun fetchGotMessages(): MutableMap<String, String> {
        val messages = settings[getMessagesKey, ""]
        val messagesString = messages.toMap()
        val mapMessages = mutableMapOf<String, String>()
        for (i in messagesString) {
            mapMessages[i.key] = i.value
        }
        return mapMessages
    }

    fun saveGotMessages(messages: String) {
        println(messages)
        settings[getMessagesKey] = messages
    }

    fun fetchSavedMessages(): Map<String, String> {
        val messages = settings[messagesKey, ""]
        val messagesString = messages.toMap()
        val mapMessages = mutableMapOf<String, String>()
        for (i in messagesString) {
            mapMessages[i.key] = i.value
        }
        println(mapMessages)
        return mapMessages
    }


    fun fetchMTexts(): Map<String, String> {
        val texts = settings[mTextsKey, ""]
        return texts.toMap()
    }

    fun saveMTexts(mText: String) {
        settings[mTextsKey] = mText
    }

    fun fetchLifecycle(): String {
        return settings[lifecycleKey, ""]
    }

    fun saveLifecycle(lifecycle: String) {
        settings[lifecycleKey] = lifecycle
    }

    fun fetchIsInChat(): String {
        return settings[isInChatKey, ""]
    }

    fun saveIsInChat(isInChat: String) {
        settings[isInChatKey] = isInChat
    }

    companion object {
        const val messagesKey = "messagesKey"
        const val getMessagesKey = "getMessagesKey"
        const val mTextsKey = "mTextsKey"
        const val lifecycleKey = "lifecycleKey"
        const val isInChatKey = "isInChatKey"
    }
}