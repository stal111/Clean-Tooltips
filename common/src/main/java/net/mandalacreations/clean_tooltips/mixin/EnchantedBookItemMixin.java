package net.mandalacreations.clean_tooltips.mixin;

import net.mandalacreations.clean_tooltips.client.EnchantmentSection;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

/**
 * @author stal111
 * @since 2023-06-04
 */
@Mixin(EnchantedBookItem.class)
public class EnchantedBookItemMixin {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;appendEnchantmentNames(Ljava/util/List;Lnet/minecraft/nbt/ListTag;)V"), method = "appendHoverText")
    private void cleanTooltips_appendEnchantmentNames(List<Component> list, ListTag listTag) {
        if (!EnchantmentSection.create(list, listTag, true)) {
            ItemStack.appendEnchantmentNames(list, listTag);
        }
    }
}
