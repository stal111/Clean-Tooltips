package net.mandalacreations.clean_tooltips.client;

import net.mandalacreations.clean_tooltips.CleanTooltips;
import net.mandalacreations.clean_tooltips.client.config.ClientConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author stal111
 * @since 2023-04-25
 */
public class EnchantmentSection extends TooltipSection {

    private static final Component ENCHANTMENTS = Component.translatable("item.clean_tooltips.enchantments").withStyle(ChatFormatting.GRAY);
    private static final MutableComponent SPACE = Component.literal(" ");

    private final ListTag enchantmentTag;
    private final List<Component> curses = new ArrayList<>();

    private final boolean isEnchantedBook;

    public EnchantmentSection(List<Component> tooltip, ListTag enchantmentTag, boolean isEnchantedBook) {
        super(tooltip, ClientConfig.INSTANCE.enchantmentSectionEnabled());
        this.enchantmentTag = enchantmentTag;
        this.isEnchantedBook = isEnchantedBook;
    }

    public static boolean create(List<Component> tooltip, ListTag enchantmentTag, boolean isEnchantedBook) {
        var section = new EnchantmentSection(tooltip, enchantmentTag, isEnchantedBook);

        section.create();

        return section.isEnabled();
    }

    @Override
    protected void buildSection() {
        for (int i = 0; i < this.enchantmentTag.size(); i++) {
            CompoundTag tag = this.enchantmentTag.getCompound(i);

            BuiltInRegistries.ENCHANTMENT.getOptional(EnchantmentHelper.getEnchantmentId(tag)).ifPresent(enchantment -> {
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
            this.addComponent(enchantment, component, this.curses::add);

            return;
        }

        this.addComponent(enchantment, component, this::addComponent);
    }

    private void addComponent(Enchantment enchantment, Component component, Consumer<Component> consumer) {
        consumer.accept(component);

        if (CleanTooltips.ENCHANTMENT_DESCRIPTIONS_LOADED && this.isEnchantedBook) {
            this.getDescriptionKey(enchantment).ifPresent(key -> {
                consumer.accept(SPACE.copy().append(Component.translatable(key).withStyle(ChatFormatting.DARK_GRAY)));
            });
        }
    }

    private Optional<String> getDescriptionKey(Enchantment enchantment) {
        String key = enchantment.getDescriptionId() + ".desc";

        if (!I18n.exists(key)) {
            if (!I18n.exists(enchantment.getDescriptionId() + ".description")) {
                return Optional.empty();
            }

            key = enchantment.getDescriptionId() + ".description";
        }

        return Optional.of(key);
    }

    private ChatFormatting getColor(Enchantment enchantment, int level) {
        if (enchantment.isCurse()) {
            return ClientConfig.INSTANCE.curseEnchantmentColor().get();
        }

        return level >= enchantment.getMaxLevel() ? ClientConfig.INSTANCE.maxLevelEnchantmentColor().get() : ClientConfig.INSTANCE.normalEnchantmentColor().get();
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
