package net.zapp.prore.register.blocks.custom.pipes.Energy;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.zapp.prore.register.blocks.custom.pipes.PipeData;

public class EnergyPipeTileRenderer implements BlockEntityRenderer<EnergyPipeTile> {

    private final BlockEntityRendererProvider.Context context;

    public EnergyPipeTileRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }
    @Override
    public void render(EnergyPipeTile tile, float partialTick, PoseStack stack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();

        PipeData data = tile.getNewData();

        final BlockRenderDispatcher dispatcher = this.context.getBlockRenderDispatcher();


        if (tile.getBlockState().getValue(EnergyPipeBlock.WEST)) {
            if (data.get(7) == 1) {
                stack.pushPose();
                stack.translate(0.125f, 0.5f, 0.5f);
                stack.scale(0.25f, 0.50f, 0.50f);
                renderer.renderStatic(new ItemStack(Blocks.RED_STAINED_GLASS), ItemDisplayContext.NONE, LightTexture.FULL_BRIGHT,
                        OverlayTexture.NO_OVERLAY, stack, bufferSource, tile.getLevel(), 1);
                stack.popPose();
            } else {
                stack.pushPose();
                stack.translate(0.125f, 0.5f, 0.5f);
                stack.scale(0.25f, 0.50f, 0.50f);
                renderer.renderStatic(new ItemStack(Blocks.GLASS), ItemDisplayContext.NONE, LightTexture.FULL_BRIGHT,
                        OverlayTexture.NO_OVERLAY, stack, bufferSource, tile.getLevel(), 1);
                stack.popPose();
            }
        }

        if (tile.getBlockState().getValue(EnergyPipeBlock.EAST)) {
            if (data.get(8) == 1) {
                stack.pushPose();
                stack.translate(0.875f, 0.5f, 0.5f);
                stack.scale(0.25f, 0.50f, 0.50f);
                renderer.renderStatic(new ItemStack(Blocks.RED_STAINED_GLASS), ItemDisplayContext.NONE, LightTexture.FULL_BRIGHT,
                        OverlayTexture.NO_OVERLAY, stack, bufferSource, tile.getLevel(), 1);
                stack.popPose();
            } else {
                stack.pushPose();
                stack.translate(0.875f, 0.5f, 0.5f);
                stack.scale(0.25f, 0.50f, 0.50f);
                renderer.renderStatic(new ItemStack(Blocks.GLASS), ItemDisplayContext.NONE, LightTexture.FULL_BRIGHT,
                        OverlayTexture.NO_OVERLAY, stack, bufferSource, tile.getLevel(), 1);
                stack.popPose();
            }
        }

        if (tile.getBlockState().getValue(EnergyPipeBlock.DOWN)) {
            if (data.get(4) == 1) {
                stack.pushPose();
                stack.translate(0.5f, 0.125f, 0.5f);
                stack.scale(0.50f, 0.25f, 0.50f);
                renderer.renderStatic(new ItemStack(Blocks.RED_STAINED_GLASS), ItemDisplayContext.NONE, LightTexture.FULL_BRIGHT,
                        OverlayTexture.NO_OVERLAY, stack, bufferSource, tile.getLevel(), 1);
                stack.popPose();
            } else {
                stack.pushPose();
                stack.translate(0.5f, 0.125f, 0.5f);
                stack.scale(0.50f, 0.25f, 0.50f);
                renderer.renderStatic(new ItemStack(Blocks.GLASS), ItemDisplayContext.NONE, LightTexture.FULL_BRIGHT,
                        OverlayTexture.NO_OVERLAY, stack, bufferSource, tile.getLevel(), 1);
                stack.popPose();
            }

        }

        if (tile.getBlockState().getValue(EnergyPipeBlock.UP)) {
            if (data.get(3) == 1) {
                stack.pushPose();
                stack.translate(0.5f, 0.875f, 0.5f);
                stack.scale(0.50f, 0.25f, 0.50f);
                renderer.renderStatic(new ItemStack(Blocks.RED_STAINED_GLASS), ItemDisplayContext.NONE, LightTexture.FULL_BRIGHT,
                        OverlayTexture.NO_OVERLAY, stack, bufferSource, tile.getLevel(), 1);
                stack.popPose();
            } else {
                stack.pushPose();
                stack.translate(0.5f, 0.875f, 0.5f);
                stack.scale(0.50f, 0.25f, 0.50f);
                renderer.renderStatic(new ItemStack(Blocks.GLASS), ItemDisplayContext.NONE, LightTexture.FULL_BRIGHT,
                        OverlayTexture.NO_OVERLAY, stack, bufferSource, tile.getLevel(), 1);
                stack.popPose();
            }

        }

        if (tile.getBlockState().getValue(EnergyPipeBlock.NORTH)) {
            if (data.get(5) == 1) {
                stack.pushPose();
                stack.translate(0.5f, 0.5f, 0.125f);
                stack.scale(0.50f, 0.50f, 0.25f);
                renderer.renderStatic(new ItemStack(Blocks.RED_STAINED_GLASS), ItemDisplayContext.NONE, LightTexture.FULL_BRIGHT,
                        OverlayTexture.NO_OVERLAY, stack, bufferSource, tile.getLevel(), 1);
                stack.popPose();
            } else {
                stack.pushPose();
                stack.translate(0.5f, 0.5f, 0.125f);
                stack.scale(0.50f, 0.50f, 0.25f);
                renderer.renderStatic(new ItemStack(Blocks.GLASS), ItemDisplayContext.NONE, LightTexture.FULL_BRIGHT,
                        OverlayTexture.NO_OVERLAY, stack, bufferSource, tile.getLevel(), 1);
                stack.popPose();
            }
        }

        if (tile.getBlockState().getValue(EnergyPipeBlock.SOUTH)) {
            if (data.get(6) == 1) {
                stack.pushPose();
                stack.translate(0.5f, 0.5f, 0.875f);
                stack.scale(0.50f, 0.50f, 0.25f);
                renderer.renderStatic(new ItemStack(Blocks.RED_STAINED_GLASS), ItemDisplayContext.NONE, LightTexture.FULL_BRIGHT,
                        OverlayTexture.NO_OVERLAY, stack, bufferSource, tile.getLevel(), 1);
                stack.popPose();
            } else {
                stack.pushPose();
                stack.translate(0.5f, 0.5f, 0.875f);
                stack.scale(0.50f, 0.50f, 0.25f);
                renderer.renderStatic(new ItemStack(Blocks.GLASS), ItemDisplayContext.NONE, LightTexture.FULL_BRIGHT,
                        OverlayTexture.NO_OVERLAY, stack, bufferSource, tile.getLevel(), 1);
                stack.popPose();
            }
        }



        int size = tile.getEnergyStorage().getEnergyStored();
        int scale = tile.data.get(1);

        if (size > 0) {
            stack.pushPose();
            stack.translate(0.5F, 0.5F, 0.5F);
            stack.scale((float) size / scale * 0.25f, (float) size / scale * 0.25f, (float) size / scale * 0.25f);
            renderer.renderStatic(new ItemStack(Blocks.LIME_STAINED_GLASS), ItemDisplayContext.NONE, LightTexture.FULL_BRIGHT,
                    OverlayTexture.NO_OVERLAY, stack, bufferSource, tile.getLevel(), 1);
            stack.popPose();
        }
    }
}
