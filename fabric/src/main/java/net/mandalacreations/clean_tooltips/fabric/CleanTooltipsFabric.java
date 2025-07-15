package net.mandalacreations.clean_tooltips.fabric;

import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.mandalacreations.clean_tooltips.CleanTooltips;
import net.mandalacreations.clean_tooltips.client.config.ClientConfig;
import net.neoforged.fml.config.ModConfig;

public class CleanTooltipsFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CleanTooltips.init();

        ConfigRegistry.INSTANCE.register(CleanTooltips.MOD_ID, ModConfig.Type.CLIENT, ClientConfig.SPEC);
    }
}