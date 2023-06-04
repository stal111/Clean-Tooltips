package net.mandalacreations.clean_tooltips.fabric;

import net.fabricmc.loader.api.FabricLoader;

/**
 * @author stal111
 * @since 2023-06-04
 */
public class PlatformUtilsImpl {

    public static boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }
}
