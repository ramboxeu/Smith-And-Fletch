package rambox.smithandfletch.entity;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class BlockEntities {
    public static BlockEntityType<SmithingTableBlockEntity> SMITHING_TABLE_BLOCK_ENTITY;

    public static void register(){
        SMITHING_TABLE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY, "smithandfletch:smithing_table", BlockEntityType.Builder.create(SmithingTableBlockEntity::new, Blocks.SMITHING_TABLE).build(null));
    }
}
