package net.mandalacreations.clean_tooltips.neoforge

import net.mandalacreations.clean_tooltips.ConfigManager
import net.mandalacreations.clean_tooltips.Constants
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent
import java.util.function.Consumer

@Mod(Constants.MOD_ID)
class CleanTooltips(eventBus: IEventBus) {

    init {
        val eventConsumer: Consumer<RegisterClientReloadListenersEvent> = Consumer {
            it.registerReloadListener(ConfigManager)
        }

        eventBus.addListener(eventConsumer)
    }
}