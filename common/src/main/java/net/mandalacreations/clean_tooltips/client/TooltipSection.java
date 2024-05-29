package net.mandalacreations.clean_tooltips.client;


import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * @author stal111
 * @since 2023-04-25
 */
public abstract class TooltipSection {

    private final Consumer<Component> consumer;
    private final ModConfigSpec.BooleanValue enabled;

    protected TooltipSection(Consumer<Component> consumer, ModConfigSpec.BooleanValue enabled) {
        this.consumer = consumer;
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
        this.consumer.accept(component);
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
        //TODO
        return false;
    }

    public boolean isEnabled() {
        return this.enabled.get();
    }
}
