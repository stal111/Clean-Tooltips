package net.mandalacreations.clean_tooltips.fabric;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.mandalacreations.clean_tooltips.CleanTooltips;
import net.fabricmc.api.ModInitializer;
import net.mandalacreations.clean_tooltips.client.config.ClientConfig;
import net.minecraftforge.fml.config.ModConfig;

public class CleanTooltipsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CleanTooltips.init();

        ForgeConfigRegistry.INSTANCE.register(CleanTooltips.MOD_ID, ModConfig.Type.CLIENT, ClientConfig.SPEC);
    }
}