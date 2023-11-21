package net.zapp.prore.register.blocks.custom.electric_furnace;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.zapp.prore.ProcessingReprocessing;
import net.zapp.prore.register.items.ItemRegister;

public class ElectricFurnaceTileRenderer implements BlockEntityRenderer<ElectricFurnaceTile> {

    protected static final ResourceLocation YOUR_TEXTURE_RESOURCE_LOCATION = new ResourceLocation(ProcessingReprocessing.MOD_ID, "textures/block/electric_furnace.png");
    public ElectricFurnaceTileRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    private final BlockEntityRendererProvider.Context context;

    private final ItemStack localItem = new ItemStack(Items.IRON_INGOT);

    private static final float constant = 0.03124f;

    @Override
    public void render(ElectricFurnaceTile pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        renderItem(pPoseStack, pBuffer, Direction.UP, pBlockEntity);
        renderItem(pPoseStack, pBuffer, Direction.DOWN, pBlockEntity);
        renderItem(pPoseStack, pBuffer, Direction.NORTH, pBlockEntity);
        renderItem(pPoseStack, pBuffer, Direction.SOUTH, pBlockEntity);
        renderItem(pPoseStack, pBuffer, Direction.EAST, pBlockEntity);
        renderItem(pPoseStack, pBuffer, Direction.WEST, pBlockEntity);
    }

    protected void renderItem(PoseStack pPoseStack, MultiBufferSource pBuffer, Direction direction, ElectricFurnaceTile pBlockEntity) {
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        pPoseStack.pushPose();

        ItemStack stack = new ItemStack(Items.AIR);

        switch (direction) {
            case UP -> {
                pPoseStack.translate(0.5, 1 - constant, 0.5);
                pPoseStack.mulPose(Axis.XP.rotationDegrees(270));
                stack = getUp(pBlockEntity);}
            case DOWN -> {
                pPoseStack.translate(0.5, constant, 0.5);
                pPoseStack.mulPose(Axis.XP.rotationDegrees(270));
                pPoseStack.mulPose(Axis.YP.rotationDegrees(180));
                stack = getDown(pBlockEntity);}
            case NORTH -> {
                pPoseStack.translate(0.5, 0.5, constant);
                pPoseStack.mulPose(Axis.YP.rotationDegrees(180));
                stack = getNorth(pBlockEntity);}
            case SOUTH -> {
                pPoseStack.translate(0.5, 0.5, 1 - constant);
                stack = getSouth(pBlockEntity);}
            case EAST -> {
                pPoseStack.translate(1 - constant, 0.5, 0.5);
                pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
                stack = getEast(pBlockEntity);}
            case WEST -> {
                pPoseStack.translate(constant, 0.5, 0.5);
                pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
                pPoseStack.mulPose(Axis.YP.rotationDegrees(180));
                stack = getWest(pBlockEntity);}
            default -> System.out.println("Invalid direction");
        }


        renderer.renderStatic(stack, ItemDisplayContext.NONE, LightTexture.FULL_BRIGHT,
                OverlayTexture.NO_WHITE_U, pPoseStack, pBuffer, pBlockEntity.getLevel(), 1);
        pPoseStack.popPose();
    }

    private ItemStack getUp(ElectricFurnaceTile pBlockEntity) {
        return translateStateToStack(pBlockEntity.data.get(4));
    }
    private ItemStack getDown(ElectricFurnaceTile pBlockEntity) {
        return translateStateToStack(pBlockEntity.data.get(5));
    }
    private ItemStack getNorth(ElectricFurnaceTile pBlockEntity) {
        if (Direction.NORTH != pBlockEntity.getFacingDirection() && Direction.NORTH != pBlockEntity.getFacingDirection().getOpposite()) {
            if (Direction.NORTH == pBlockEntity.getFacingDirection().getClockWise()) {
                return translateStateToStack(pBlockEntity.data.get(7));
            } else {
                return translateStateToStack(pBlockEntity.data.get(6));
            }
        }
        return translateStateToStack(0);
    }
    private ItemStack getSouth(ElectricFurnaceTile pBlockEntity) {
        if (Direction.SOUTH != pBlockEntity.getFacingDirection() && Direction.SOUTH != pBlockEntity.getFacingDirection().getOpposite()) {
            if (Direction.SOUTH == pBlockEntity.getFacingDirection().getClockWise()) {
                return translateStateToStack(pBlockEntity.data.get(7));
            } else {
                return translateStateToStack(pBlockEntity.data.get(6));
            }
        }
        return translateStateToStack(0);
    }
    private ItemStack getEast(ElectricFurnaceTile pBlockEntity) {
        if (Direction.EAST != pBlockEntity.getFacingDirection() && Direction.EAST != pBlockEntity.getFacingDirection().getOpposite()) {
            if (Direction.EAST == pBlockEntity.getFacingDirection().getClockWise()) {
                return translateStateToStack(pBlockEntity.data.get(7));
            } else {
                return translateStateToStack(pBlockEntity.data.get(6));
            }
        }
        return translateStateToStack(0);
    }
    private ItemStack getWest(ElectricFurnaceTile pBlockEntity) {
        if (Direction.WEST != pBlockEntity.getFacingDirection() && Direction.WEST != pBlockEntity.getFacingDirection().getOpposite()) {
            if (Direction.WEST == pBlockEntity.getFacingDirection().getClockWise()) {
                return translateStateToStack(pBlockEntity.data.get(7));
            } else {
                return translateStateToStack(pBlockEntity.data.get(6));
            }
        }
        return translateStateToStack(0);
    }

    private ItemStack translateStateToStack(int i) {
        return switch (i) {
            case 1 -> new ItemStack(ItemRegister.CONFIGURATION1.get());
            case 2 -> new ItemStack(ItemRegister.CONFIGURATION2.get());
            case 3 -> new ItemStack(ItemRegister.CONFIGURATION3.get());
            case 4 -> new ItemStack(ItemRegister.CONFIGURATION4.get());
            case 5 -> new ItemStack(ItemRegister.CONFIGURATION5.get());
            case 6 -> new ItemStack(ItemRegister.CONFIGURATION6.get());
            case 7 -> new ItemStack(ItemRegister.CONFIGURATION7.get());
            case 8 -> new ItemStack(ItemRegister.CONFIGURATION8.get());
            case 9 -> new ItemStack(ItemRegister.CONFIGURATION9.get());
            default -> new ItemStack(Items.AIR);
        };
    }
}