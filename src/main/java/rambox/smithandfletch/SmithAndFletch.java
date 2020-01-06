package rambox.smithandfletch;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rambox.smithandfletch.entity.BlockEntities;

public class SmithAndFletch implements ModInitializer {
    public static final Logger LOGGER = LogManager.getFormatterLogger("SmithAndFletch");

    @Override
    public void onInitialize() {
        LOGGER.info("Add some functionality to Smithing and Fletching Tables!");
        BlockEntities.register();
    }
}
