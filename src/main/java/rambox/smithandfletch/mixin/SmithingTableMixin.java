package rambox.smithandfletch.mixin;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.SmithingTableBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rambox.smithandfletch.SmithAndFletch;
import rambox.smithandfletch.block.entity.SmithingTableBlockEntity;
import rambox.smithandfletch.client.container.Containers;

@Mixin(SmithingTableBlock.class)
public class SmithingTableMixin extends CraftingTableBlock implements BlockEntityProvider {

    protected SmithingTableMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> callbackInfoReturnable) {
        if (world.isClient) {
            callbackInfoReturnable.setReturnValue(ActionResult.SUCCESS);
        } else {
            ContainerProviderRegistry.INSTANCE.openContainer(Containers.SMITHING_TABLE_CONTAINER, player, buf -> buf.writeBlockPos(pos));
            callbackInfoReturnable.setReturnValue(ActionResult.SUCCESS);
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new SmithingTableBlockEntity();
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {

        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof SmithingTableBlockEntity) {
            return ((SmithingTableBlockEntity) blockEntity).calculateRedstonePower();
        }

        return 0;
    }

    @Override
    public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof SmithingTableBlockEntity) {
                ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), ((SmithingTableBlockEntity) blockEntity).inventory.get(0));
                SmithAndFletch.LOGGER.info(itemEntity.toString());
                world.spawnEntity(itemEntity);
            }

            world.removeBlockEntity(pos);
        }
    }
}
