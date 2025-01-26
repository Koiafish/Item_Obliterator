package elocindev.item_obliterator.fabric_quilt.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import elocindev.item_obliterator.fabric_quilt.util.Utils;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"), 
            method = "playerTick", locals = LocalCapture.CAPTURE_FAILHARD)
    public void item_obliterator$removeItemFromInventory(CallbackInfo info, int i) {
        ServerPlayerEntity player = ((ServerPlayerEntity)(Object)this);
        ItemStack item = player.getInventory().getStack(i);

        if (Utils.isDisabled(item)) {
            item.setCount(0); // Removes the item from the inventory.
            player.sendMessageToClient(Text.translatable("item_obliterator.disabled_item"), true); // Notify the player.
        }
    }
}
