import Jsoup.JsoupMainRemoteDataSource
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton
import settings.SettingsMainDataSource

val mainModule = DI.Module("mainModule") {
    bind<SettingsMainDataSource>() with provider {
        SettingsMainDataSource(instance())
    }

    bind<JsoupMainRemoteDataSource>() with provider {
        JsoupMainRemoteDataSource(instance())
    }

    bind<MainRepository>() with singleton {
        MainRepositoryImpl(instance(), instance())
    }
}