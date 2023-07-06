interface AuthRepository {
    suspend fun login(login: String, password: String): Map<String, String>

    fun fetchCookies(): Map<String, String>
    fun saveCookies(cookies: String)
//
//    fun fetchName(): String
//    fun saveName(name: String)
}