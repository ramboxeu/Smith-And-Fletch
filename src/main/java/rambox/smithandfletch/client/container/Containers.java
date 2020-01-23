package rambox.smithandfletch.client.container;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.util.Identifier;
import rambox.smithandfletch.block.entity.SmithingTableBlockEntity;

public class Containers {
    public static final Identifier SMITHING_TABLE_CONTAINER = new Identifier("smithandfletch:smithing_table");
    public static final Identifier FLETCHING_TABLE_CONTAINER = new Identifier("smithandfletch:fletching_table");

    public static void register(){
        ContainerProviderRegistry.INSTANCE.registerFactory(SMITHING_TABLE_CONTAINER, (syncid, identifier, player, buf) -> new SmithingTableContainer(syncid, player.inventory, (SmithingTableBlockEntity) player.world.getBlockEntity(buf.readBlockPos())));
        ContainerProviderRegistry.INSTANCE.registerFactory(FLETCHING_TABLE_CONTAINER, (syncid, identifier, player, buf) -> new FletchingTableContainer(syncid));
    }
}
