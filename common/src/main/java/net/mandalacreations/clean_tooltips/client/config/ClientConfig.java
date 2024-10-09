package net.mandalacreations.clean_tooltips.client.config;

import net.minecraft.ChatFormatting;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author stal111
 * @since 2023-05-07
 */
public record ClientConfig(ModConfigSpec.BooleanValue durabilitySectionEnabled,
                           ModConfigSpec.BooleanValue enchantmentSectionEnabled,
                           ModConfigSpec.EnumValue<ChatFormatting> curseEnchantmentColor,
                           ModConfigSpec.EnumValue<ChatFormatting> normalEnchantmentColor,
                           ModConfigSpec.EnumValue<ChatFormatting> maxLevelEnchantmentColor,
                           ModConfigSpec.BooleanValue colorSectionEnabled,
                           ModConfigSpec.BooleanValue gapEnabled) {

    public static final IConfigSpec SPEC;
    public static final ClientConfig INSTANCE;

    static {
        Pair<ClientConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(ClientConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }

    public ClientConfig(ModConfigSpec.Builder build) {
        this(
                build.comment("Should the Durability section be enabled?").define("durability.enabled", true),
                build.comment("Should the fancied up Enchantment section be used?").define("enchantments.enabled", true),
                build.comment("The color curses should have").defineEnum("enchantments.color.curse", ChatFormatting.RED),
                build.comment("The color normal enchantments should have").defineEnum("enchantments.color.normal", ChatFormatting.GREEN),
                build.comment("The color max level enchantments should have").defineEnum("enchantments.color.max_level", ChatFormatting.GOLD),
                build.comment("Should the fancied up Color section be used?").define("color.enabled", true),
                build.comment("Should sections of the tooltip have spacing between them?").define("gap.enabled", true)
        );
    }
}
