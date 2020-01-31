package rambox.smithandfletch.client.container;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.container.BlockContext;
import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.LingeringPotionItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import rambox.smithandfletch.SmithAndFletch;

public class FletchingTableContainer extends Container {
    private final Inventory inputInventory;
    public final Inventory outputInventory;
    private Runnable inventoryChangeListener;
    private final BlockContext context;
    private final Slot effectSlot;
    private final Slot arrowSlot;
    private final Slot outputSlot;

    public FletchingTableContainer(int syncId, PlayerInventory playerInventory, BlockContext blockContext) {
        super(null, syncId);

        this.inputInventory = new BasicInventory(2) {
            @Override
            public void markDirty() {
                super.markDirty();
                FletchingTableContainer.this.onContentChanged(this);
                inventoryChangeListener.run();
            }
        };
        this.outputInventory = new BasicInventory(1) {
            @Override
            public void markDirty() {
                super.markDirty();
                inventoryChangeListener.run();
            }
        };

        this.inventoryChangeListener = () -> {};

        this.context = blockContext;

        this.effectSlot = this.addSlot(new Slot(inputInventory, 0, 30, 26) {
            @Override
            public boolean canInsert(ItemStack stack) {
                Potion potion = PotionUtil.getPotion(stack);

                return (stack.getItem() instanceof LingeringPotionItem &&
                        !(potion.equals(Potions.AWKWARD) || potion.equals(Potions.EMPTY) || potion.equals(Potions.WATER) || potion.equals(Potions.THICK) || potion.equals(Potions.MUNDANE)
                ) || stack.isItemEqual(new ItemStack(Items.GLOWSTONE_DUST)));
            }
        });

        this.arrowSlot = this.addSlot(new Slot(inputInventory, 1, 30, 45) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isItemEqual(new ItemStack(Items.ARROW));
            }
        });

        this.outputSlot = this.addSlot(new Slot(outputInventory, 0, 130, 36) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
                FletchingTableContainer.this.effectSlot.takeStack(1);
                FletchingTableContainer.this.arrowSlot.takeStack(8);

                return super.onTakeItem(player, stack);
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

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.context.run((world, blockPos) -> {
            SmithAndFletch.LOGGER.info("Dropping inventory!");
            this.dropInventory(player, player.world, inputInventory);
        });
    }

    @Environment(EnvType.CLIENT)
    public void setInventoryChangeListener(Runnable inventoryChangeListener) {
        this.inventoryChangeListener = inventoryChangeListener;
    }

    public void onContentChanged(Inventory inventory) {
        ItemStack effect = this.effectSlot.getStack();
        ItemStack arrows = this.arrowSlot.getStack();
        ItemStack output;

        if (!effect.isEmpty() && !arrows.isEmpty()) {
            if (effect.getItem() instanceof LingeringPotionItem) {
                output = new ItemStack(Items.TIPPED_ARROW, arrows.getCount() >= 8 ? 8 : 0);
                PotionUtil.setPotion(output, PotionUtil.getPotion(effect));
            } else {
                output = new ItemStack(Items.SPECTRAL_ARROW, arrows.getCount() >= 8 ? 8 : 0);
            }

            if (!outputSlot.getStack().isItemEqual(output)) {
                outputSlot.setStack(output);
            }
        } else {
            outputSlot.setStack(ItemStack.EMPTY);
        }
    }
}
