package net.mandalacreations.clean_tooltips;

import dev.architectury.injectables.annotations.ExpectPlatform;

/**
 * @author stal111
 * @since 2023-06-04
 */
public class PlatformUtils {

    @ExpectPlatform
    public static boolean isModLoaded(String modId) {
        throw new AssertionError();
    }
}
