package net.mandalacreations.clean_tooltips.client;

import net.mandalacreations.clean_tooltips.client.config.ClientConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

/**
 * @author stal111
 * @since 2023-04-25
 */
public class ColorSection extends TooltipSection {

    private static final Component COLOR = Component.translatable("item.color", "").withStyle(ChatFormatting.GRAY);

    private final int color;

    protected ColorSection(Consumer<Component> consumer, int color) {
        super(consumer, ClientConfig.INSTANCE.colorSectionEnabled());
        this.color = color;
    }

    public static void create(Consumer<Component> consumer, int color) {
        new ColorSection(consumer, color).create();
    }

    @Override
    protected void buildSection() {
        this.addComponent(COLOR.copy().append(Component.literal(String.format(Locale.ROOT, "#%06X", this.color)).withStyle(style -> style.withColor(this.color))));
    }
}
