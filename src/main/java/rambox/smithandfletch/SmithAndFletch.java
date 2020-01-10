package rambox.smithandfletch;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rambox.smithandfletch.block.entity.BlockEntities;
import rambox.smithandfletch.client.container.Containers;

public class SmithAndFletch implements ModInitializer {
    public static final Logger LOGGER = LogManager.getFormatterLogger("SmithAndFletch");

    @Override
    public void onInitialize() {
        LOGGER.info("Adding some functionality to Smithing and Fletching Tables!");
        BlockEntities.register();
        Containers.register();
    }
}
