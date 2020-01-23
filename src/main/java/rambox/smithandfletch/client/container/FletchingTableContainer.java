package rambox.smithandfletch.client.container;

import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;

public class FletchingTableContainer extends Container {
    private Inventory inputInventory;
    private Inventory outputInventory;

    protected FletchingTableContainer(int syncId) {
        super(null, syncId);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
