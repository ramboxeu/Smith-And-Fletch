package rambox.smithandfletch.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.AbstractContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import rambox.smithandfletch.block.entity.SmithingTableBlockEntity;
import rambox.smithandfletch.client.container.SmithingTableContainer;

public class SmithingTableScreen extends AbstractContainerScreen {
    public Identifier smithingTableGui = new Identifier("smithandfletch:textures/gui/smithing_table.png");

    private SmithingTableBlockEntity blockEntity;

    public SmithingTableScreen(SmithingTableContainer container, SmithingTableBlockEntity blockEntity, PlayerInventory playerInventory) {
        super(container, playerInventory, new LiteralText("Smithing Table"));

        this.blockEntity = blockEntity;
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        super.render(mouseX, mouseY, delta);
        this.drawMouseoverTooltip(mouseX, mouseY);
    }

    @Override
    protected void drawBackground(float delta, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(smithingTableGui);
        int i = (this.width - this.containerWidth) / 2;
        int j = (this.height - this.containerHeight) / 2;
        this.blit(i, j, 0, 0, this.containerWidth, this.containerHeight);
    }
    @Override
    protected void drawForeground(int mouseX, int mouseY) {
        String title = "Smithing Table";

        this.font.draw(title, 10F, 6.0F, 4210752);
        this.font.draw(this.playerInventory.getDisplayName().asFormattedString(), 8.0F, (float)(this.containerHeight - 96 + 2), 4210752);
        renderToolInfo();
    }

    private void renderToolInfo(){
        ItemStack tool = this.blockEntity.inventory.get(0);
        if (!tool.isEmpty()) {
            this.font.draw("Durability:", 88F, 17F, 14342874);
            this.font.draw(String.format("%d/%d", (tool.getMaxDamage() - tool.getDamage()), tool.getMaxDamage()),  88F, 27F, 14342874);
            this.font.draw("Repair cost:", 88F, 45F, 14342874);
            this.font.draw(String.format("%d XP", tool.getRepairCost()), 88F, 55F, 14342874);
        }
    }
}
