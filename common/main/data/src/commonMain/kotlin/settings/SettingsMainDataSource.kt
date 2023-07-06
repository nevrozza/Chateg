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

    fun fetchGotMessages(): Map<String, Int> {
        val messages = settings[getMessagesKey, ""]
        val messagesString = messages.toMap()
        val mapMessages = mutableMapOf<String, Int>()
        for (i in messagesString) {
            mapMessages[i.key] = i.value.toInt()
        }
        return mapMessages
    }

    fun saveGotMessages(messages: String) {
        settings[getMessagesKey] = messages
    }

    fun fetchSavedMessages(): Map<String, Int> {
        val messages = settings[messagesKey, ""]
        val messagesString = messages.toMap()
        val mapMessages = mutableMapOf<String, Int>()
        for (i in messagesString) {
            mapMessages[i.key] = i.value.toInt()
        }
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

    fun fetchIsInChat(): Boolean {
        return settings[isInChatKey, false]
    }

    fun saveIsInChat(isInChat: Boolean) {
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