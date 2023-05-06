package net.mandalacreations.clean_tooltips.fabric;

import net.mandalacreations.clean_tooltips.CleanTooltips;
import net.fabricmc.api.ModInitializer;

public class CleanTooltipsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CleanTooltips.init();
    }
}