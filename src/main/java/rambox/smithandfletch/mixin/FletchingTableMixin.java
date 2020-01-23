package rambox.smithandfletch.mixin;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.FletchingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rambox.smithandfletch.client.container.Containers;

@Mixin(FletchingTableBlock.class)
public class FletchingTableMixin {
    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> callbackInfoReturnable) {
        if (world.isClient) {
            callbackInfoReturnable.setReturnValue(ActionResult.SUCCESS);
        } else {
            ContainerProviderRegistry.INSTANCE.openContainer(Containers.FLETCHING_TABLE_CONTAINER, player, buf -> buf.writeBlockPos(pos));
            callbackInfoReturnable.setReturnValue(ActionResult.SUCCESS);
        }
    }
}
