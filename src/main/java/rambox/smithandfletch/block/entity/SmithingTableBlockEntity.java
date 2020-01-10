package rambox.smithandfletch.block.entity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

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
