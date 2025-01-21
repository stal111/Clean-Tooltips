package net.mandalacreations.clean_tooltips.tooltip.config

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.mandalacreations.clean_tooltips.tooltip.ColorDefinition
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.ComponentSerialization

data class DurabilityConfig(
    val enabled: Boolean,
    val startWithEmptyLine: Boolean,
    val component: Component,
    val colors: List<ColorDefinition>
) : TooltipConfig {

    override fun isEnabled() = enabled

    override fun hasEmptyStartingLine() = startWithEmptyLine

    companion object {
        val CODEC: Codec<DurabilityConfig> = RecordCodecBuilder.create { it.group(
            Codec.BOOL.fieldOf("enabled").forGetter(DurabilityConfig::enabled),
            Codec.BOOL.fieldOf("start_with_empty_line").forGetter { it.startWithEmptyLine },
            ComponentSerialization.CODEC.fieldOf("component").forGetter { it.component },
            Codec.list(ColorDefinition.CODEC).fieldOf("colors").forGetter { it.colors }
        ).apply(it, ::DurabilityConfig) }
    }
}