package net.zapp.prore.register.blocks.custom.machine.electric_furnace;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.zapp.prore.ProcessingReprocessing;

public class ElectricFurnaceScreen extends AbstractContainerScreen<ElectricFurnaceMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ProcessingReprocessing.MOD_ID, "textures/gui/electric_furnace.png");

    protected int lastProgress = menu.tile.data.get(0);
    protected int usage = menu.getEnergyUsage();
    protected int capacity = menu.tile.data.get(3);
    protected float storedEnergy = menu.getEnergy();
    protected final static int ENERGY_BAR_HEIGHT = 55;
    protected int up;
    protected int down;
    protected int left;
    protected int right;
    protected int push_pull;

    public ElectricFurnaceScreen(ElectricFurnaceMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.imageWidth = 230;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();

        up = menu.tile.data.get(4);
        down = menu.tile.data.get(5);
        left = menu.tile.data.get(6);
        right = menu.tile.data.get(7);
        push_pull = menu.tile.data.get(8);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        addCustomButton(x + 209, y + 69, 10, 10, "up", () -> up = rollButton(up, 4));
        addCustomButton(x + 209, y + 87, 10, 10, "down", () -> down = rollButton(down, 5));
        addCustomButton(x + 209, y + 105, 10, 10, "left", () -> left = rollButton(left, 6));
        addCustomButton(x + 209, y + 123, 10, 10, "right", () -> right = rollButton(right, 7));
        addCustomButton(x + 206, y + 142, 16, 16, "push/pull", () -> push_pull = rollButtonPushPull(push_pull, 8));
    }


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {

        int ScaledStoredEnergy = Math.round((storedEnergy / capacity) * ENERGY_BAR_HEIGHT);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        guiGraphics.blit(TEXTURE, x + 9, y + 78 + ENERGY_BAR_HEIGHT - ScaledStoredEnergy, 230, 17 + ENERGY_BAR_HEIGHT - ScaledStoredEnergy, 14, ScaledStoredEnergy);


        if (menu.isWorking()) {
            if (menu.getMaximumProgress() != 1) {
                guiGraphics.blit(TEXTURE, x + 101, y + 35, 230, 0, menu.getScaledProgress(), 17);
            } else {
                guiGraphics.blit(TEXTURE, x + 101, y + 35, 230, 0, 24, 17);
            }
        }

        buttonState(guiGraphics, x + 207, y + 67, 130, 242, 14,14, up);
        buttonState(guiGraphics, x + 207, y + 85, 130, 228, 14,14, down);
        buttonState(guiGraphics, x + 207, y + 103, 0, 228, 14, 14, left);
        buttonState(guiGraphics, x + 207, y + 121, 0, 242, 14, 14, right);
        buttonState(guiGraphics, x + 205, y + 141, 0, 210, 18, 18, push_pull);
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {

        this.storedEnergy = menu.getEnergy();
        this.usage = menu.getEnergyUsage();

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);

        renderCustomTooltip(guiGraphics, x + 7, y + 133, 4, 7, mouseX, mouseY, Component.literal(Math.round(storedEnergy) + " / " + capacity + " RF").withStyle(ChatFormatting.GRAY));
        renderCustomTooltip(guiGraphics, x + 7, y + 71, 4, 7, mouseX, mouseY, Component.translatable("prore.info.upgrades"));
        renderCustomTooltip(guiGraphics, x + 219, y + 53, 4, 7, mouseX, mouseY, Component.translatable("prore.info.arguments"));

        renderCustomTooltip(guiGraphics, x + 209, y + 69, 10, 10, mouseX, mouseY, Component.literal(Component.translatable("prore.configure.up").getString() + Component.translatable("prore.port." + up).getString()));
        renderCustomTooltip(guiGraphics, x + 209, y + 87, 10, 10, mouseX, mouseY, Component.literal(Component.translatable("prore.configure.down").getString() + Component.translatable("prore.port." + down).getString()));
        renderCustomTooltip(guiGraphics, x + 209, y + 105, 10, 10, mouseX, mouseY, Component.literal(Component.translatable("prore.configure.left").getString() + Component.translatable("prore.port." + left).getString()));
        renderCustomTooltip(guiGraphics, x + 209, y + 123, 10, 10, mouseX, mouseY, Component.literal(Component.translatable("prore.configure.right").getString() + Component.translatable("prore.port." + right).getString()));

        renderCustomTooltip(guiGraphics, x + 206, y + 142, 16, 16, mouseX, mouseY, Component.literal(Component.translatable("prore.configure.push_pull").getString() + Component.translatable("prore.push_pull." + push_pull).getString()));

        renderCustomHighlight(guiGraphics, x + 7, y + 133, 4, 7, 40, 166, mouseX, mouseY);
        renderCustomHighlight(guiGraphics, x + 7, y + 71, 4, 7, 40, 166, mouseX, mouseY);
        renderCustomHighlight(guiGraphics, x + 219, y + 53, 4, 7, 40, 166, mouseX, mouseY);

        renderCustomHighlight(guiGraphics, x + 209, y + 69, 10, 10, 0, 166, mouseX, mouseY);
        renderCustomHighlight(guiGraphics, x + 209, y + 87, 10, 10, 10, 166, mouseX, mouseY);
        renderCustomHighlight(guiGraphics, x + 209, y + 105, 10, 10, 20, 166, mouseX, mouseY);
        renderCustomHighlight(guiGraphics, x + 209, y + 123, 10, 10, 30, 166, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics p_281635_, int p_282681_, int p_283686_) {
        String currentUsage = menu.tile.data.get(9) * ((storedEnergy > 0 && menu.isWorking()) ? 1 : 0 ) + " RF/t";

        p_281635_.drawString(this.font, this.title, (imageWidth / 2) - (getTextLen(this.title.getString()) / 2), this.titleLabelY, 15596031, false);
        p_281635_.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX + 28, this.inventoryLabelY + 1, 15596031, false);
        p_281635_.drawString(this.font, currentUsage, 196 - getTextLen(currentUsage), 73, 15596031, false);
    }




    private void renderCustomTooltip(GuiGraphics guiGraphics, int x, int y, int width, int height, int mouseX, int mouseY, Component component) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            guiGraphics.renderTooltip(this.font, component, mouseX, mouseY);
        }
    }

    private void renderCustomHighlight(GuiGraphics guiGraphics, int x, int y, int width, int height, int x2, int y2, int mouseX, int mouseY) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            guiGraphics.blit(TEXTURE, x, y, x2, y2, width, height);
        }
    }

    private void addCustomButton(int x, int y, int width, int height, String text, Runnable function) {
        addWidget(new AbstractWidget(x, y,  width + 1, height + 1, Component.literal(text)) {
            @Override
            protected void renderWidget(GuiGraphics guiGraphics, int i, int j, float k) {
            }

            @Override
            protected void updateWidgetNarration(NarrationElementOutput output) {

            }

            @Override
            public void onClick(double i, double j) {
                super.onClick(i, j);
                function.run();
            }
        });
    }
    protected int rollButton(int stage, int data) {
        stage++;
        if (stage > menu.tile.getNumberOfInputs() && stage < 5) {
            stage = 5;
        } else if (stage > menu.tile.getNumberOfOutputs() + 4 && stage < 9) {
            stage = 9;
        } else if (stage > 9) {
            stage = 0;
        }
        menu.tile.data.set(data, stage);
        menu.tile.syncClientToServer(menu.tile.getBlockPos());
        return stage;
    }

    protected int rollButtonPushPull(int stage, int data) {
        stage++;
        if (stage > 3) {
            stage = 0;
        }
        menu.tile.data.set(data, stage);
        menu.tile.syncClientToServer(menu.tile.getBlockPos());
        return stage;
    }
    protected int getTextLen(String text) {
        int out = 0;
        for (int i = 0 ; i < text.length() ; i++) {
            switch (text.charAt(i)) {
                case 'I', 'k', ' ', 'f': out+=5; break;
                case 't': out+=4; break;
                case 'l': out+=3; break;
                case 'i': out+=2; break;
                default: out+=6; break;
            }
        }
        return out;
    }

    protected void buttonState(GuiGraphics guiGraphics,int x1, int y1, int x2, int y2, int width, int height, int state) {
        if (state > 0) {
            guiGraphics.blit(TEXTURE, x1, y1, x2 + (state-1) * width, y2, width, height);
        }
    }
}


