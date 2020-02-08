package rambox.smithandfletch.block.entity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Tickable;

public class SmithingTableBlockEntity extends BlockEntity implements Inventory, Tickable {
    public DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private int tickCounter = 0;

    public SmithingTableBlockEntity() {
        super(BlockEntities.SMITHING_TABLE_BLOCK_ENTITY);
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);

        inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
        Inventories.fromTag(tag, inventory);

        tickCounter = tag.getInt("tickCounter");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag){
        super.toTag(tag);

        Inventories.toTag(tag, this.inventory);
        tag.putInt("tickCounter", tickCounter);

        return tag;
    }

    @Override
    public void tick() {
        ItemStack stack = this.inventory.get(0);
        if (!stack.isEmpty()) {
            if (tickCounter == 20) {
                tickCounter = 0;

                int repair = stack.getDamage() - 1;
                if (repair <= stack.getMaxDamage()) {
                    stack.setDamage(repair);
                    this.world.updateNeighbors(this.pos, this.world.getBlockState(this.pos).getBlock());
                }
            } else {
                tickCounter++;
            }


        } else {
            tickCounter = 0;
        }
    }

    @Override
    public int getInvSize() {
        return inventory.size();
    }

    @Override
    public boolean isInvEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public ItemStack getInvStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack takeInvStack(int slot, int amount) {
        return Inventories.splitStack(this.inventory, slot, amount);
    }

    @Override
    public ItemStack removeInvStack(int slot) {
        return Inventories.removeStack(this.inventory, slot);
    }

    @Override
    public void setInvStack(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
    }

    @Override
    public boolean canPlayerUseInv(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    public int calculateRedstonePower(){
        ItemStack tool = this.inventory.get(0);
        if (!tool.isEmpty()) {
            if (tool.getDamage() > 0) {
                return (int) (((14 * ((float) (tool.getMaxDamage() - tool.getDamage()) / tool.getMaxDamage())) / 1) + 1);
            } else if (tool.getDamage() == 0) {
                return 15;
            }
        }
        return 0;
    }
}
