package rambox.smithandfletch.client.screen;

import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import rambox.smithandfletch.block.entity.SmithingTableBlockEntity;
import rambox.smithandfletch.client.container.Containers;
import rambox.smithandfletch.client.container.SmithingTableContainer;

public class Screens {
    public static void register() {
        ScreenProviderRegistry.INSTANCE.registerFactory(Containers.SMITHING_TABLE_CONTAINER, (syncid, identifier, player, buf) -> {
            SmithingTableBlockEntity smithingTableBlockEntity = (SmithingTableBlockEntity) player.world.getBlockEntity(buf.readBlockPos());
            return new SmithingTableScreen(new SmithingTableContainer(syncid, player.inventory, smithingTableBlockEntity), smithingTableBlockEntity,player.inventory);
        });
    }
}
