package rambox.smithandfletch.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.container.Container;
import net.minecraft.container.ContainerProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class SmithingTableBlockEntity extends BlockEntity implements Inventory {
    public SmithingTableBlockEntity() {
        super(BlockEntities.SMITHING_TABLE_BLOCK_ENTITY);
    }

    @Override
    public int getInvSize() {
        return 0;
    }

    @Override
    public boolean isInvEmpty() {
        return false;
    }

    @Override
    public ItemStack getInvStack(int slot) {
        return null;
    }

    @Override
    public ItemStack takeInvStack(int slot, int amount) {
        return null;
    }

    @Override
    public ItemStack removeInvStack(int slot) {
        return null;
    }

    @Override
    public void setInvStack(int slot, ItemStack stack) {

    }

    @Override
    public boolean canPlayerUseInv(PlayerEntity player) {
        return false;
    }

    @Override
    public void clear() {

    }
}
