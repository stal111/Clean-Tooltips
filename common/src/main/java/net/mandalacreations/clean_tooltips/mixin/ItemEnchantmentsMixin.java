package net.mandalacreations.clean_tooltips.mixin;

import net.mandalacreations.clean_tooltips.client.ColorSection;
import net.mandalacreations.clean_tooltips.client.EnchantmentSection;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Final;
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
@Mixin(ItemEnchantments.class)
public class ItemEnchantmentsMixin {

    @Shadow @Final
    boolean showInTooltip;

    @Inject(at = @At(value = "HEAD"), method = "addToTooltip", cancellable = true)
    private void cleanTooltips_addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, CallbackInfo ci) {
        if (this.showInTooltip) {
            ci.cancel();

            EnchantmentSection.create(consumer, (ItemEnchantments) (Object) this, false);
        }
    }
}
