package net.mandalacreations.clean_tooltips.client;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * @author stal111
 * @since 2023-04-25
 */
public class DurabilitySection extends TooltipSection {

    private static final Component DURABILITY = Component.translatable("item.clean_tooltips.durability").withStyle(ChatFormatting.GRAY);

    private final ItemStack stack;

    protected DurabilitySection(List<Component> tooltip, ItemStack stack) {
        super(tooltip);
        this.stack = stack;
    }

    public static void create(List<Component> tooltip, ItemStack stack) {
        new DurabilitySection(tooltip, stack).create();
    }

    @Override
    protected void buildSection() {
        int maxDamage = this.stack.getMaxDamage();
        int damage = this.stack.getDamageValue();

        ChatFormatting color = ChatFormatting.GREEN;

        int remainingUses = (maxDamage - damage);

        if (remainingUses <= maxDamage * 0.1) {
            color = ChatFormatting.RED;
        } else if (remainingUses <= maxDamage * 0.5) {
            color = ChatFormatting.YELLOW;
        }

        this.addComponent(DURABILITY.copy().append(Component.literal(" " + remainingUses).withStyle(color)).append(Component.literal(" / " + maxDamage)).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public boolean shouldDisplay() {
        return this.stack.isDamaged();
    }
}
