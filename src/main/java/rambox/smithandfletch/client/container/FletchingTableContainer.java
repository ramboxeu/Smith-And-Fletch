package rambox.smithandfletch.client.container;

import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class FletchingTableContainer extends Container {
    private Inventory inputInventory;
    public Inventory outputInventory;
    private Runnable inventoryChangeListener;

    public FletchingTableContainer(int syncId, PlayerInventory playerInventory) {
        super(null, syncId);

        this.inputInventory = new BasicInventory(2);
        this.outputInventory = new BasicInventory(1);

        this.inventoryChangeListener = () -> {};

        this.addSlot(new Slot(inputInventory, 0, 30, 26) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isItemEqual(new ItemStack(Items.LINGERING_POTION)) || stack.isItemEqual(new ItemStack(Items.GLOWSTONE_DUST));
            }
        });
        this.addSlot(new Slot(inputInventory, 1, 30, 45) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isItemEqual(new ItemStack(Items.ARROW));
            }
        });

        this.addSlot(new Slot(outputInventory, 0, 130, 36));

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
        Inventory inventory = inputInventory;

        if (slot != null && slot.hasStack()) {
            itemStack = slot.getStack();
            if (invSlot < inventory.getInvSize()) {
                if (!this.insertItem(itemStack, inventory.getInvSize(), this.slotList.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack, 0, inventory.getInvSize(), false)) {
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
