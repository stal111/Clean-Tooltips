package net.mandalacreations.clean_tooltips.mixin;

import net.mandalacreations.clean_tooltips.client.ColorSection;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.DyedItemColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

/**
 * @author stal111
 * @since 26.05.2024
 */
@Mixin(DyedItemColor.class)
public abstract class DyedItemColorMixin {

    @Shadow public abstract boolean showInTooltip();

    @Shadow public abstract int rgb();

    @Inject(at = @At(value = "HEAD"), method = "addToTooltip", cancellable = true)
    private void cleanTooltips_addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, CallbackInfo ci) {
        if (this.showInTooltip()) {
            ci.cancel();

            ColorSection.create(consumer, this.rgb());
        }
    }
}
