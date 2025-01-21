package net.mandalacreations.clean_tooltips.tooltip.config

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.ComponentSerialization

data class ColorConfig(
    val enabled: Boolean,
    val startWithEmptyLine: Boolean,
    val format: String,
    val component: Component
) : TooltipConfig {

    override fun isEnabled() = enabled

    override fun hasEmptyStartingLine() = startWithEmptyLine

    companion object {
        val CODEC: Codec<ColorConfig> = RecordCodecBuilder.create { it.group(
            Codec.BOOL.fieldOf("enabled").forGetter(ColorConfig::enabled),
            Codec.BOOL.fieldOf("start_with_empty_line").forGetter(ColorConfig::startWithEmptyLine),
            Codec.STRING.fieldOf("format").forGetter(ColorConfig::format),
            ComponentSerialization.CODEC.fieldOf("component").forGetter(ColorConfig::component)
        ).apply(it, ::ColorConfig) }
    }
}
