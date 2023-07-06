import di.Inject
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.direct
import org.kodein.di.singleton

object PlatformSDK {
    fun init(
        configuration: PlatformConfiguration,
        os: OS
    ) {
        val umbrellaModule = DI.Module(
            name = "umbrella"
        ) {
            bind<PlatformConfiguration>() with singleton { configuration }
            bind<OS>() with singleton { os }
        }
        Inject.createDependencies(
            DI {
                importAll(
                    umbrellaModule,
                    coreModule,
                    authModule,
                    mainModule
                )
            }.direct
        )

    }

}