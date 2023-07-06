package settings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set

class SettingsAuthDataSource(
    private val settings: Settings
) {
    fun saveCookies(cookies: String) {
        settings[cookiesKey] = cookies
    }

    fun fetchCookies(): Map<String, String>{
        val cookiesString = settings[cookiesKey, ""]
        val trimmedString = cookiesString.trim('{', '}')

        val cookies = mutableMapOf<String, String>()
        val cookiePairs = trimmedString.split(", ")

        for (cookiePair in cookiePairs) {
            val keyValue = cookiePair.split("=")
            if (keyValue.size == 2) {
                val key = keyValue[0].trim()
                val value = keyValue[1].trim()
                cookies[key] = value
            }
        }

        return cookies
    }

//    fun saveName(name: String) {
//        settings[nameKey] = name
//    }
//
//    fun fetchName(): String {
//        return settings[nameKey, ""]
//    }

    companion object {
        const val cookiesKey = "cookiesKey"
//        const val nameKey = "nameKey"
    }
}