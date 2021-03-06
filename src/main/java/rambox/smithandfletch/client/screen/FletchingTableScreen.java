package rambox.smithandfletch.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.AbstractContainerScreen;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpectralArrowItem;
import net.minecraft.item.TippedArrowItem;
import net.minecraft.potion.PotionUtil;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ChatUtil;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;
import rambox.smithandfletch.SmithAndFletch;
import rambox.smithandfletch.client.container.FletchingTableContainer;

import java.util.List;

public class FletchingTableScreen extends AbstractContainerScreen {
    public Identifier fletchingTableGui = new Identifier("smithandfletch:textures/gui/fletching_table.png");
    private FletchingTableContainer container;

    public FletchingTableScreen(FletchingTableContainer container, PlayerInventory playerInventory) {
        super(container, playerInventory, new LiteralText("Fletching Table"));
        this.container = container;
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
        minecraft.getTextureManager().bindTexture(fletchingTableGui);
        int i = (this.width - this.containerWidth) / 2;
        int j = (this.height - this.containerHeight) / 2;
        this.blit(i, j, 0, 0, this.containerWidth, this.containerHeight);
    }

    @Override
    protected void drawForeground(int mouseX, int mouseY) {
        String title = "Fletching Table";

        this.font.draw(title, 10F, 5.0F, 4210752);
        this.font.draw(this.playerInventory.getDisplayName().asFormattedString(), 8.0F, (float)(this.containerHeight - 96 + 2), 4210752);
        renderArrowInfo();
    }

    private void renderArrowInfo(){
        ItemStack arrows = container.outputInventory.getInvStack(0);
        if (!arrows.isEmpty()) {
            List<StatusEffectInstance> effects = PotionUtil.getPotionEffects(arrows);
            this.font.draw("Effects: ", 74F, 16F, 14342874);
            float y = 25F;
            if (arrows.getItem() instanceof TippedArrowItem) {
                for (StatusEffectInstance status : effects) {
                    String potionName = new TranslatableText(status.getTranslationKey()).asString();
                    String potionPotency = "";
                    String potionDuration = " (" + StatusEffectUtil.durationToString(status,    .125F) + ")";

                    if (status.getAmplifier() > 0) {
                        potionPotency = " " + (new TranslatableText("potion.potency." + status.getAmplifier())).asString();
                    }

                    potionName = potionName + potionPotency;

                    this.font.draw(potionName, 76F, y, 14342874);
                    this.font.draw(potionDuration, 131F, y + 10, 14342874);
                    y += 20;
                }
            } else if (arrows.getItem() instanceof SpectralArrowItem) {
                this.font.draw("Glowing (0:10)", 76F, y, 14342874);
            }
        }
    }
}
