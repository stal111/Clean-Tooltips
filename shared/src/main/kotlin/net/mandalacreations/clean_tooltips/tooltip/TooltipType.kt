package net.mandalacreations.clean_tooltips.tooltip

import com.mojang.serialization.Codec
import net.mandalacreations.clean_tooltips.tooltip.config.ColorConfig
import net.mandalacreations.clean_tooltips.tooltip.config.DurabilityConfig
import net.mandalacreations.clean_tooltips.tooltip.config.EnchantmentConfig
import net.mandalacreations.clean_tooltips.tooltip.config.TooltipConfig
import java.util.*

enum class TooltipType(val codec: Codec<out TooltipConfig>) {
    COLOR(ColorConfig.CODEC),
    DURABILITY(DurabilityConfig.CODEC),
    ENCHANTMENT(EnchantmentConfig.CODEC);

    val configName = name.lowercase(Locale.getDefault())
}