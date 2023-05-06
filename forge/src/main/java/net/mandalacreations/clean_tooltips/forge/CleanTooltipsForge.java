package net.mandalacreations.clean_tooltips.forge;

import net.mandalacreations.clean_tooltips.CleanTooltips;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.network.NetworkConstants;

@Mod(CleanTooltips.MOD_ID)
public class CleanTooltipsForge {
    public CleanTooltipsForge() {
        CleanTooltips.init();
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = CleanTooltips.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    private static class ClientEvents {

        @SubscribeEvent
        public static void constructMod(FMLConstructModEvent event) {
            ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));
        }
    }
}