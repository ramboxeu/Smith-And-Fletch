package rambox.smithandfletch;

import net.fabricmc.api.ClientModInitializer;
import rambox.smithandfletch.client.screen.Screens;

public class SmithAndFletchClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Screens.register();
    }
}
