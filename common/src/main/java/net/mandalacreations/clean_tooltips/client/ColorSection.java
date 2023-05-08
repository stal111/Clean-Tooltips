package net.mandalacreations.clean_tooltips.client;

import net.mandalacreations.clean_tooltips.client.config.ClientConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.Locale;

/**
 * @author stal111
 * @since 2023-04-25
 */
public class ColorSection extends TooltipSection {

    private static final Component COLOR = Component.translatable("item.color", "").withStyle(ChatFormatting.GRAY);

    private final int color;

    protected ColorSection(List<Component> tooltip, int color) {
        super(tooltip, ClientConfig.INSTANCE.colorSectionEnabled());
        this.color = color;
    }

    public static void create(List<Component> tooltip, int color) {
        new ColorSection(tooltip, color).create();
    }

    @Override
    protected void buildSection() {
        this.addComponent(COLOR.copy().append(Component.literal(String.format(Locale.ROOT, "#%06X", this.color)).withStyle(style -> style.withColor(this.color))));
    }
}
