package rambox.smithandfletch.mixin;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.SmithingTableBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rambox.smithandfletch.entity.SmithingTableBlockEntity;

@Mixin(SmithingTableBlock.class)
@Implements(value = @Interface(iface = BlockEntityProvider.class, prefix = "entityProvider$"))
public class SmithingTableMixin implements BlockEntityProvider {

    @Inject(method = "onUse", at = @At("HEAD"))
    private void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> callbackInfoReturnable) {
        if (world.isClient) {
            callbackInfoReturnable.setReturnValue(ActionResult.SUCCESS);
        } else {
            // open gui
            callbackInfoReturnable.setReturnValue(ActionResult.SUCCESS);
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new SmithingTableBlockEntity();
    }
}
