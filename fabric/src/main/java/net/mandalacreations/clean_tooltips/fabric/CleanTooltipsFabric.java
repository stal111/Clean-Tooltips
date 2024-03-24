package net.mandalacreations.clean_tooltips.fabric;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.mandalacreations.clean_tooltips.CleanTooltips;
import net.mandalacreations.clean_tooltips.client.config.ClientConfig;
import net.neoforged.fml.config.ModConfig;

public class CleanTooltipsFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CleanTooltips.init();

        NeoForgeConfigRegistry.INSTANCE.register(CleanTooltips.MOD_ID, ModConfig.Type.CLIENT, ClientConfig.SPEC);
    }
}