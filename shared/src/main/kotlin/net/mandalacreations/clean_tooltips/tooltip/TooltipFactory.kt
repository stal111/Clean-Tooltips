package net.mandalacreations.clean_tooltips.tooltip

import net.mandalacreations.clean_tooltips.ConfigManager
import net.mandalacreations.clean_tooltips.tooltip.config.ColorConfig
import net.mandalacreations.clean_tooltips.tooltip.config.DurabilityConfig
import net.mandalacreations.clean_tooltips.tooltip.config.EnchantmentConfig
import net.mandalacreations.clean_tooltips.tooltip.config.TooltipConfig
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.ItemEnchantments

data class TooltipFactory<C : TooltipConfig>(val section: TooltipSection<C>, val configClass: Class<C>) {

    fun buildTooltip(consumer: (Component) -> Unit) {
        val config = ConfigManager.CONFIGS[section.getType()] ?: return

        if (section.shouldDisplay() && configClass.isInstance(config) && config.isEnabled()) {
            if (config.hasEmptyStartingLine()) {
                consumer(CommonComponents.SPACE)
            }
            section.buildSection(configClass.cast(config)).forEach { consumer(it) }
        }
    }

    companion object {
        @JvmStatic
        fun createColor(color: Int, consumer: (Component) -> Unit) =
            TooltipFactory(
                ColorSection(color), ColorConfig::class.java
            ).buildTooltip(consumer)

        @JvmStatic
        fun createDurability(stack: ItemStack, consumer: (Component) -> Unit) =
            TooltipFactory(
                DurabilitySection(stack),
                DurabilityConfig::class.java
            ).buildTooltip(consumer)

        @JvmStatic
        fun createEnchantment(enchantments: ItemEnchantments, isEnchantedBook: Boolean, consumer: (Component) -> Unit) =
            TooltipFactory(
                EnchantmentSection(enchantments, isEnchantedBook),
                EnchantmentConfig::class.java
            ).buildTooltip(consumer)
    }
}