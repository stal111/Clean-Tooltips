package net.mandalacreations.clean_tooltips.client;

import net.mandalacreations.clean_tooltips.CleanTooltips;
import net.mandalacreations.clean_tooltips.client.config.ClientConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
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

    private final ItemEnchantments enchantments;
    private final List<Component> curses = new ArrayList<>();

    private final boolean isEnchantedBook;

    public EnchantmentSection(Consumer<Component> consumer, ItemEnchantments enchantments, boolean isEnchantedBook) {
        super(consumer, ClientConfig.INSTANCE.enchantmentSectionEnabled());
        this.enchantments = enchantments;
        this.isEnchantedBook = isEnchantedBook;
    }

    public static boolean create(Consumer<Component> consumer, ItemEnchantments enchantments, boolean isEnchantedBook) {
        var section = new EnchantmentSection(consumer, enchantments, isEnchantedBook);

        section.create();

        return section.isEnabled();
    }

    @Override
    protected void buildSection() {
        this.enchantments.entrySet().forEach(entry -> {
            this.handleEnchantment(entry.getKey(), entry.getIntValue());
        });

        this.curses.forEach(this::addComponent);
    }

    private void handleEnchantment(Holder<Enchantment> enchantment, int level) {
        ChatFormatting color = this.getColor(enchantment, level);

        Component component = SPACE.copy().append(Enchantment.getFullname(enchantment, level).copy().withStyle(color));

        if (enchantment.is(EnchantmentTags.CURSE)) {
            this.addComponent(enchantment, component, this.curses::add);

            return;
        }

        this.addComponent(enchantment, component, this::addComponent);
    }

    private void addComponent(Holder<Enchantment> enchantment, Component component, Consumer<Component> consumer) {
        consumer.accept(component);

        if (CleanTooltips.ENCHANTMENT_DESCRIPTIONS_LOADED && this.isEnchantedBook) {
            this.getDescriptionKey(enchantment.value()).ifPresent(key -> {
                consumer.accept(SPACE.copy().append(Component.translatable(key).withStyle(ChatFormatting.DARK_GRAY)));
            });
        }
    }

    private Optional<String> getDescriptionKey(Enchantment enchantment) {
        String key = enchantment.description().getString() + ".desc";

        if (!I18n.exists(key)) {
            if (!I18n.exists(enchantment.description().getString() + ".description")) {
                return Optional.empty();
            }

            key = enchantment.description().getString() + ".description";
        }

        return Optional.of(key);
    }

    private ChatFormatting getColor(Holder<Enchantment> enchantment, int level) {
        if (enchantment.is(EnchantmentTags.CURSE)) {
            return ClientConfig.INSTANCE.curseEnchantmentColor().get();
        }

        return level >= enchantment.value().getMaxLevel() ? ClientConfig.INSTANCE.maxLevelEnchantmentColor().get() : ClientConfig.INSTANCE.normalEnchantmentColor().get();
    }

    @Override
    public boolean shouldDisplay() {
        return !this.enchantments.isEmpty();
    }

    @Override
    protected @Nullable Component getHeader() {
        return ENCHANTMENTS;
    }
}
