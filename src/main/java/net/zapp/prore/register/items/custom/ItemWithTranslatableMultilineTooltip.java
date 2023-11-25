package net.zapp.prore.register.items.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemWithTranslatableMultilineTooltip extends Item {
    private final String key;
    private final Style Gray = Style.EMPTY.withColor(ChatFormatting.GRAY);
    private final Style DarkGray = Style.EMPTY.withColor(ChatFormatting.DARK_GRAY);
    private final Style Green = Style.EMPTY.withColor(ChatFormatting.GREEN);
    public ItemWithTranslatableMultilineTooltip(Properties p_41383_, String key) {
        super(p_41383_);
        this.key = key;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if(!Screen.hasShiftDown()) {
            components.add(Component.translatable(key + ".desc").withStyle(Gray));
            components.add(Component.translatable("tooltip.shift").withStyle(Green));
        } else {
            int i = 1;
            while (!Component.translatable(key + ".tooltip." + i).getString().equals(key + ".tooltip." + i) || i > 100) {
                components.add(Component.translatable(key + ".tooltip." + i).withStyle(DarkGray));
                i++;
            }
        }
        super.appendHoverText(stack, level, components, flag);
    }
}
