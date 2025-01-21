package net.mandalacreations.clean_tooltips.tooltip

import net.mandalacreations.clean_tooltips.tooltip.config.ColorConfig
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component
import java.util.*

data class ColorSection(val color: Int) : TooltipSection<ColorConfig> {

    override fun buildSection(config: ColorConfig) = listOf(
        config.component.copy()
            .append(CommonComponents.SPACE)
            .append(Component.literal(config.format.format(Locale.ROOT, this.color)).withColor(this.color))
    )

    override fun getType() = TooltipType.COLOR
}