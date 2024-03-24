package net.mandalacreations.clean_tooltips.neoforge;

import net.mandalacreations.clean_tooltips.CleanTooltips;
import net.mandalacreations.clean_tooltips.client.config.ClientConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.IExtensionPoint;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;

@Mod(CleanTooltips.MOD_ID)
public class CleanTooltipsForge {
    public CleanTooltipsForge() {
        CleanTooltips.init();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = CleanTooltips.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    private static class ClientEvents {

        @SubscribeEvent
        public static void constructMod(FMLConstructModEvent event) {
            ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> IExtensionPoint.DisplayTest.IGNORESERVERONLY, (a, b) -> true));
        }
    }
}