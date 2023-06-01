package net.mandalacreations.clean_tooltips.client.config;

import net.minecraft.ChatFormatting;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author stal111
 * @since 2023-05-07
 */
public record ClientConfig(BooleanValue durabilitySectionEnabled,
                           BooleanValue enchantmentSectionEnabled,
                           ForgeConfigSpec.EnumValue<ChatFormatting> curseEnchantmentColor,
                           ForgeConfigSpec.EnumValue<ChatFormatting> normalEnchantmentColor,
                           ForgeConfigSpec.EnumValue<ChatFormatting> maxLevelEnchantmentColor,
                           BooleanValue colorSectionEnabled) {

    public static final ForgeConfigSpec SPEC;
    public static final ClientConfig INSTANCE;

    static {
        Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }

    public ClientConfig(ForgeConfigSpec.Builder build) {
        this(
                build.comment("Should the Durability section be enabled?").define("durability.enabled", true),
                build.comment("Should the fancied up Enchantment section be used?").define("enchantments.enabled", true),
                build.comment("The color curses should have").defineEnum("enchantments.color.curse", ChatFormatting.RED),
                build.comment("The color normal enchantments should have").defineEnum("enchantments.color.normal", ChatFormatting.GREEN),
                build.comment("The color max level enchantments should have").defineEnum("enchantments.color.max_level", ChatFormatting.GOLD),
                build.comment("Should the fancied up Color section be used?").define("color.enabled", true)
        );
    }
}
