package net.mandalacreations.clean_tooltips.neoforge;

import net.neoforged.fml.ModList;

/**
 * @author stal111
 * @since 2023-06-04
 */
public class PlatformUtilsImpl {

    public static boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }
}