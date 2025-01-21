package net.mandalacreations.clean_tooltips.tooltip

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.mandalacreations.clean_tooltips.tooltip.config.DurabilityConfig
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack

data class DurabilitySection(val stack: ItemStack) : TooltipSection<DurabilityConfig> {

    override fun buildSection(config: DurabilityConfig): List<Component> {
        val maxDamage = stack.maxDamage
        val damage = stack.damageValue

        val remainingUses = maxDamage - damage
        val color = getColor(remainingUses, maxDamage, config.colors)

        return listOf(
            config.component.copy().append(CommonComponents.SPACE)
                .append(Component.literal("$remainingUses").withStyle(color))
                .append(Component.literal(" / $maxDamage").withStyle(ChatFormatting.GRAY))
        )
    }

    override fun getType() = TooltipType.DURABILITY

    fun getColor(remainingUses: Int, maxDamage: Int, colors: List<ColorDefinition>) =
        colors.firstOrNull { it.isInRange(remainingUses.toDouble() / maxDamage) }?.color ?: ChatFormatting.WHITE

    override fun shouldDisplay() = stack.isDamaged
}

data class ColorDefinition(val color: ChatFormatting, val threshold: Double) {
    fun isInRange(value: Double) = value <= threshold

    companion object {
        val CODEC: Codec<ColorDefinition> = RecordCodecBuilder.create {
            it.group(
                ChatFormatting.CODEC.fieldOf("color").forGetter(ColorDefinition::color),
                Codec.DOUBLE.fieldOf("threshold").forGetter(ColorDefinition::threshold)
            ).apply(it, ::ColorDefinition)
        }
    }
}