package rambox.smithandfletch;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rambox.smithandfletch.block.entity.BlockEntities;
import rambox.smithandfletch.client.container.Containers;
import rambox.smithandfletch.config.Config;

public class SmithAndFletch implements ModInitializer {
    public static final Logger LOGGER = LogManager.getFormatterLogger("SmithAndFletch");
    public static Config config;

    @Override
    public void onInitialize() {
        LOGGER.info("Adding some functionality to Smithing and Fletching Tables!");

        config = Config.load();

        BlockEntities.register();
        Containers.register();
    }
}
