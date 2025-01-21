package net.mandalacreations.clean_tooltips.tooltip.config

interface TooltipConfig {

    fun isEnabled(): Boolean

    fun hasEmptyStartingLine(): Boolean
}