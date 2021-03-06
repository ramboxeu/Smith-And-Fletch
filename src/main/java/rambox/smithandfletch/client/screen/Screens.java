package rambox.smithandfletch.client.screen;

import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.container.BlockContext;
import rambox.smithandfletch.block.entity.SmithingTableBlockEntity;
import rambox.smithandfletch.client.container.Containers;
import rambox.smithandfletch.client.container.FletchingTableContainer;
import rambox.smithandfletch.client.container.SmithingTableContainer;

public class Screens {
    public static void register() {
        ScreenProviderRegistry.INSTANCE.registerFactory(Containers.SMITHING_TABLE_CONTAINER, (syncid, identifier, player, buf) -> {
            SmithingTableBlockEntity smithingTableBlockEntity = (SmithingTableBlockEntity) player.world.getBlockEntity(buf.readBlockPos());
            return new SmithingTableScreen(new SmithingTableContainer(syncid, player.inventory, smithingTableBlockEntity), smithingTableBlockEntity,player.inventory);
        });

        ScreenProviderRegistry.INSTANCE.registerFactory(Containers.FLETCHING_TABLE_CONTAINER, (syncid, identifier, player, buf) -> new FletchingTableScreen(new FletchingTableContainer(syncid, player.inventory, BlockContext.EMPTY), player.inventory));
    }
}
