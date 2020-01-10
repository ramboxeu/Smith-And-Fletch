package rambox.smithandfletch.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.AbstractContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import rambox.smithandfletch.client.container.SmithingTableContainer;

public class SmithingTableScreen extends AbstractContainerScreen {
    public Identifier smithingTableGui = new Identifier("smithandfletch:textures/gui/smithing_table.png");

    public SmithingTableScreen(SmithingTableContainer container, PlayerInventory playerInventory) {
        super(container, playerInventory, new LiteralText("Smithing Table"));
    }

    @Override
    protected void drawBackground(float delta, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(smithingTableGui);
        int i = (this.width - this.containerWidth) / 2;
        int j = (this.height - this.containerHeight) / 2;
        this.blit(i, j, 0, 0, this.containerWidth, this.containerHeight);
    }
}
