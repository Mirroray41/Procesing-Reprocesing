package net.zapp.prore.register.items.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemWithTranslatableTooltip extends Item {
    private final String key;
    private final Style Gray = Style.EMPTY.withColor(ChatFormatting.GRAY);
    public ItemWithTranslatableTooltip(Properties p_41383_, String key) {
        super(p_41383_);
        this.key = key;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable(key + ".desc").withStyle(Gray));
        super.appendHoverText(stack, level, components, flag);
    }
}
