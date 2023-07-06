import Jsoup.JsoupAuthRemoteDataSource
import Jsoup.JsoupChategLoginRequest
import settings.SettingsAuthDataSource

class AuthRepositoryImpl(
    private val remoteDataSource: JsoupAuthRemoteDataSource,
    private val cacheDataSource: SettingsAuthDataSource
) : AuthRepository {
    override suspend fun login(login: String, password: String): Map<String, String> {
        return remoteDataSource.perjormChategLogin(JsoupChategLoginRequest(login, password))
    }

    override fun fetchCookies(): Map<String, String> {
        return cacheDataSource.fetchCookies()
    }

    override fun saveCookies(cookies: String) {
        cacheDataSource.saveCookies(cookies)
    }

//    override fun fetchName(): String {
//        return cacheDataSource.fetchName()
//    }
//
//    override fun saveName(name: String) {
//        cacheDataSource.saveName(name)
//    }
}