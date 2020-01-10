package rambox.smithandfletch.client.screen;

import net.minecraft.client.gui.screen.ingame.AbstractContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import rambox.smithandfletch.client.container.SmithingTableContainer;

public class SmithingTableScreen extends AbstractContainerScreen {
    public SmithingTableScreen(SmithingTableContainer container, PlayerInventory playerInventory) {
        super(container, playerInventory, new LiteralText("Smithing Table"));
    }

    @Override
    protected void drawBackground(float delta, int mouseX, int mouseY) {

    }
}
