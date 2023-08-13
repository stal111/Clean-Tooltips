package net.mandalacreations.clean_tooltips.mixin;

import net.mandalacreations.clean_tooltips.client.ColorSection;
import net.mandalacreations.clean_tooltips.client.DurabilitySection;
import net.mandalacreations.clean_tooltips.client.EnchantmentSection;
import net.mandalacreations.clean_tooltips.client.config.ClientConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

/**
 * @author stal111
 * @since 2023-04-19
 */
@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow @Nullable private CompoundTag tag;

    @Shadow protected abstract int getHideFlags();

    @Shadow
    private static boolean shouldShowInTooltip(int i, ItemStack.TooltipPart tooltipPart) {
        return false;
    }

    @Shadow public abstract Item getItem();

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;appendEnchantmentNames(Ljava/util/List;Lnet/minecraft/nbt/ListTag;)V"), method = "getTooltipLines")
    private void cleanTooltips_appendEnchantmentNames(List<Component> list, ListTag listTag) {
        if (!EnchantmentSection.create(list, listTag, false)) {
            ItemStack.appendEnchantmentNames(list, listTag);
        }
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;contains(Ljava/lang/String;I)Z", ordinal = 1), method = "getTooltipLines")
    private boolean cleanTooltips_getTooltipLines$cancelColorInfo(CompoundTag instance, String string, int i) {
        return !ClientConfig.INSTANCE.colorSectionEnabled().get() && instance.contains("color", 99);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;getCompound(Ljava/lang/String;)Lnet/minecraft/nbt/CompoundTag;"), method = "getTooltipLines", locals = LocalCapture.CAPTURE_FAILSOFT)
    private void cleanTooltips_getTooltipLines(Player player, TooltipFlag tooltipFlag, CallbackInfoReturnable<List<Component>> cir, List<Component> list) {
        CompoundTag tag = this.tag.getCompound("display");

        if (shouldShowInTooltip(this.getHideFlags(), ItemStack.TooltipPart.DYE) && tag.contains("color", 99)) {
            ColorSection.create(list, tag.getInt("color"));
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shouldShowInTooltip(ILnet/minecraft/world/item/ItemStack$TooltipPart;)Z", ordinal = 2), method = "getTooltipLines", locals = LocalCapture.CAPTURE_FAILSOFT)
    private void cleanTooltips_getTooltipLines$addDurabilityTooltip(Player player, TooltipFlag tooltipFlag, CallbackInfoReturnable<List<Component>> cir, List<Component> list) {
        DurabilitySection.create(list, (ItemStack) (Object) this);
    }
}
