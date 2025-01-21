package net.mandalacreations.clean_tooltips.tooltip

import net.mandalacreations.clean_tooltips.tooltip.config.TooltipConfig
import net.minecraft.network.chat.Component

interface TooltipSection<C: TooltipConfig> {

    fun buildSection(config: C): List<Component>

    fun shouldDisplay(): Boolean = true

    fun getType(): TooltipType
}