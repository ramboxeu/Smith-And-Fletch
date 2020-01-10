package rambox.smithandfletch.client.screen;

import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.util.Identifier;
import rambox.smithandfletch.block.entity.SmithingTableBlockEntity;
import rambox.smithandfletch.client.container.Containers;
import rambox.smithandfletch.client.container.SmithingTableContainer;

public class Screens {
    public static void register() {
        ScreenProviderRegistry.INSTANCE.registerFactory(Containers.SMITHING_TABLE_CONTAINER, (syncid, identifier, player, buf) -> new SmithingTableScreen(new SmithingTableContainer(syncid, player.inventory, (SmithingTableBlockEntity) player.world.getBlockEntity(buf.readBlockPos())), player.inventory));
    }
}
