import di.Inject
import org.kodein.di.DI
import org.kodein.di.direct

object PlatformSDK {
    fun init() {
        val umbrellaModule = DI.Module(
            name = "umbrella"
        ) {}
        Inject.createDependencies(
            DI {
                importAll(
                    umbrellaModule,
                    coreModule
                )
            }.direct
        )

    }

}