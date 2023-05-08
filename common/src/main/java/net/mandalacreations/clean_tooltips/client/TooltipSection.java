package net.mandalacreations.clean_tooltips.client;


import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author stal111
 * @since 2023-04-25
 */
public abstract class TooltipSection {

    private final List<Component> tooltip;
    private final ForgeConfigSpec.BooleanValue enabled;

    protected TooltipSection(List<Component> tooltip, ForgeConfigSpec.BooleanValue enabled) {
        this.tooltip = tooltip;
        this.enabled = enabled;
    }

    public void create() {
        if (this.enabled.get() && this.shouldDisplay()) {
            if (!this.isFirstSection()) {
                this.addComponent(Component.empty());
            }

            this.addHeader();

            this.buildSection();
        }
    }

    protected abstract void buildSection();

    protected void addComponent(Component component) {
        this.tooltip.add(component);
    }

    public boolean shouldDisplay() {
        return true;
    }

    private void addHeader() {
        Component header = this.getHeader();

        if (header == null) {
            return;
        }

        this.addComponent(header);
    }

    @Nullable
    protected Component getHeader() {
        return null;
    }

    protected boolean isFirstSection() {
        return this.tooltip.size() <= 1;
    }

    public boolean isEnabled() {
        return this.enabled.get();
    }
}
