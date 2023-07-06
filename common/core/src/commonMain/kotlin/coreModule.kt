import org.kodein.di.DI
import settings.settingsModule

var coreModule = DI.Module("coreModule") {
    importAll(
        settingsModule
    )
}