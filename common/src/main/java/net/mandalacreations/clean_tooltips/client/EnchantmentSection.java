package net.mandalacreations.clean_tooltips.client;

import net.mandalacreations.clean_tooltips.client.config.ClientConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author stal111
 * @since 2023-04-25
 */
public class EnchantmentSection extends TooltipSection {

    private static final Component ENCHANTMENTS = Component.translatable("item.clean_tooltips.enchantments").withStyle(ChatFormatting.GRAY);
    private static final MutableComponent SPACE = Component.literal(" ");

    private final ListTag enchantmentTag;
    private final List<Component> curses = new ArrayList<>();

    public EnchantmentSection(List<Component> tooltip, ListTag enchantmentTag) {
        super(tooltip, ClientConfig.INSTANCE.enchantmentSectionEnabled());
        this.enchantmentTag = enchantmentTag;
    }

    public static boolean create(List<Component> tooltip, ListTag enchantmentTag) {
        var section = new EnchantmentSection(tooltip, enchantmentTag);

        section.create();

        return section.isEnabled();
    }

    @Override
    protected void buildSection() {
        for (int i = 0; i < this.enchantmentTag.size(); i++) {
            CompoundTag tag = this.enchantmentTag.getCompound(i);

            Registry.ENCHANTMENT.getOptional(EnchantmentHelper.getEnchantmentId(tag)).ifPresent(enchantment -> {
                this.handleEnchantment(enchantment, tag);
            });
        }

        this.curses.forEach(this::addComponent);
    }

    private void handleEnchantment(Enchantment enchantment, CompoundTag tag) {
        int level = EnchantmentHelper.getEnchantmentLevel(tag);
        ChatFormatting color = this.getColor(enchantment, level);

        Component component = SPACE.copy().append(enchantment.getFullname(level).copy().withStyle(color));

        if (enchantment.isCurse()) {
            this.curses.add(component);

            return;
        }

        this.addComponent(component);
    }

    private ChatFormatting getColor(Enchantment enchantment, int level) {
        return enchantment.isCurse() ? ChatFormatting.RED : level == enchantment.getMaxLevel() ? ChatFormatting.GOLD : ChatFormatting.GREEN;
    }

    @Override
    public boolean shouldDisplay() {
        return !this.enchantmentTag.isEmpty();
    }

    @Override
    protected @Nullable Component getHeader() {
        return ENCHANTMENTS;
    }
}
