package rambox.smithandfletch.client.container;

import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;
import rambox.smithandfletch.SmithAndFletch;

public class SmithingTableContainer extends Container {
    private final Inventory inventory;

    public SmithingTableContainer(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(null, syncId);
        this.inventory = inventory;

        this.addSlot(new Slot(inventory, 0, 36, 33) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return (stack.isDamageable() && !SmithAndFletch.config.getBlacklist().contains(Registry.ITEM.getId(stack.getItem())));
            }
        });

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slotList.get(invSlot);

        if (slot != null && slot.hasStack()) {
            itemStack = slot.getStack();
            if (invSlot < this.inventory.getInvSize()) {
                if (!this.insertItem(itemStack, this.inventory.getInvSize(), this.slotList.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack, 0, this.inventory.getInvSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return itemStack;
    }
}
