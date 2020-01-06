package rambox.smithandfletch;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SmithAndFletch implements ModInitializer {
    public static final Logger log = LogManager.getFormatterLogger("SmithAndFletch");

    @Override
    public void onInitialize() {
        log.info("Add some functionality to Smithing and Fletching Tables!");
    }
}
