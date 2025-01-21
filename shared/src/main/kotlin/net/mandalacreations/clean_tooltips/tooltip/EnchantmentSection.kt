package net.mandalacreations.clean_tooltips.tooltip

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.mandalacreations.clean_tooltips.tooltip.config.EnchantmentConfig
import net.minecraft.ChatFormatting
import net.minecraft.core.Holder
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.tags.EnchantmentTags
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.item.enchantment.ItemEnchantments

data class EnchantmentSection(
    val enchantments: ItemEnchantments,
    val isEnchantedBook: Boolean
) : TooltipSection<EnchantmentConfig> {

    override fun buildSection(config: EnchantmentConfig): List<Component> = listOf(config.component) + enchantments.entrySet()
        .sortedBy { it.key.`is`(EnchantmentTags.CURSE) }
        .map { (enchantment, level) -> handleEnchantment(enchantment, level, config.colors) }

    fun handleEnchantment(enchantment: Holder<Enchantment>, level: Int, colors: Colors): Component {
        val color = getColor(enchantment, level, colors)
        val name = Enchantment.getFullname(enchantment, level)

        if (name is MutableComponent) {
            name.withStyle(color)
        }

        return CommonComponents.space().append(name)
    }

    override fun getType() = TooltipType.ENCHANTMENT

    fun getColor(enchantment: Holder<Enchantment>, level: Int, colors: Colors) = when {
        enchantment.`is`(EnchantmentTags.CURSE) -> colors.curse
        level == enchantment.value().maxLevel -> colors.max
        level > enchantment.value().maxLevel -> colors.overMax
        else -> colors.default
    }

    override fun shouldDisplay() = !enchantments.isEmpty

    data class Colors(val default: ChatFormatting, val max: ChatFormatting, val overMax: ChatFormatting, val curse: ChatFormatting) {
        companion object {
            val CODEC: Codec<Colors> = RecordCodecBuilder.create {
                it.group(
                    ChatFormatting.CODEC.fieldOf("default").forGetter { it.default },
                    ChatFormatting.CODEC.fieldOf("max").forGetter { it.max },
                    ChatFormatting.CODEC.fieldOf("over_max").forGetter { it.overMax },
                    ChatFormatting.CODEC.fieldOf("curse").forGetter { it.curse }
                ).apply(it, ::Colors)
            }
        }
    }
}