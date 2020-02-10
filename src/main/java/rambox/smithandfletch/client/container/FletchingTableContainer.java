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
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;

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

        this.effectSlot = this.addSlot(new Slot(inputInventory, 0, 15, 26) {
            @Override
            public boolean canInsert(ItemStack stack) {
                Potion potion = PotionUtil.getPotion(stack);

                return (stack.getItem() instanceof LingeringPotionItem &&
                        !(potion.equals(Potions.AWKWARD) || potion.equals(Potions.EMPTY) || potion.equals(Potions.WATER) || potion.equals(Potions.THICK) || potion.equals(Potions.MUNDANE)
                ) || stack.getItem().equals(Items.GLOWSTONE_DUST));
            }
        });

        this.arrowSlot = this.addSlot(new Slot(inputInventory, 1, 15, 45) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isItemEqual(new ItemStack(Items.ARROW));
            }
        });

        this.outputSlot = this.addSlot(new Slot(outputInventory, 0, 44, 34) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
                ItemStack craft = this.getStack();
                if (craft.getItem() instanceof SpectralArrowItem) {
                    FletchingTableContainer.this.effectSlot.takeStack(4);
                    FletchingTableContainer.this.arrowSlot.takeStack(1);
                } else if (craft.getItem() instanceof TippedArrowItem) {
                    FletchingTableContainer.this.effectSlot.takeStack(1);
                    FletchingTableContainer.this.arrowSlot.takeStack(8);
                }

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
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (invSlot == this.outputSlot.id) {
                if (!this.insertItem(itemStack2, 4, 34, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onStackChanged(itemStack2, itemStack);
            } else if (invSlot != this.effectSlot.id && invSlot != this.arrowSlot.id) {
                if (itemStack2.getItem() instanceof ArrowItem) {
                    if (!this.insertItem(itemStack2, this.arrowSlot.id, this.arrowSlot.id + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (itemStack2.getItem() instanceof LingeringPotionItem || itemStack2.getItem().equals(Items.GLOWSTONE_DUST)) {
                    if (!this.insertItem(itemStack2, this.effectSlot.id, this.effectSlot.id + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (invSlot >= 4 && invSlot < 34) {
                    if (!this.insertItem(itemStack2, 31, 34, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.insertItem(itemStack2, 4, 34, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, itemStack2);
        }

        return itemStack;
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.context.run((world, blockPos) -> {
            this.dropInventory(player, player.world, inputInventory);
        });
    }

    @Environment(EnvType.CLIENT)
    public void setInventoryChangeListener(Runnable inventoryChangeListener) {
        this.inventoryChangeListener = inventoryChangeListener;
    }

    public void onContentChanged(Inventory inventory) {
        this.updateOutputSlot();
        this.sendContentUpdates();
    }

    private void updateOutputSlot(){
        ItemStack effect = this.effectSlot.getStack();
        ItemStack arrows = this.arrowSlot.getStack();
        ItemStack output = ItemStack.EMPTY;

        if (!effect.isEmpty() && !arrows.isEmpty()) {
            if (effect.getItem() instanceof LingeringPotionItem && arrows.getCount() >= 8) {
                output = new ItemStack(Items.TIPPED_ARROW, 8);
                PotionUtil.setPotion(output, PotionUtil.getPotion(effect));
            } else if (effect.getItem().equals(Items.GLOWSTONE_DUST) && arrows.getCount() >= 2) {
                output = new ItemStack(Items.SPECTRAL_ARROW, 8);
            }

            if (!ItemStack.areEqualIgnoreDamage(output, this.outputSlot.getStack())) {
                this.outputSlot.setStack(output);
            }
        } else {
            this.outputSlot.setStack(ItemStack.EMPTY);
        }
    }
}
