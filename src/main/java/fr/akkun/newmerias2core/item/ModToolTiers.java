package fr.akkun.newmerias2core.item;

import fr.akkun.newmerias2core.NewmeriaS2Core;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public class ModToolTiers {
    /** Tools that count as "made of sapphire" for gameplay checks (e.g. what the Snow Walker is weak to). */
    public static final TagKey<Item> SAPPHIRE_TOOLS =
            TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(NewmeriaS2Core.MOD_ID, "sapphire_tools"));

    /** What repairs a sapphire tool on an anvil / with a grindstone. */
    public static final TagKey<Item> SAPPHIRE_TOOL_MATERIALS =
            TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(NewmeriaS2Core.MOD_ID, "sapphire_tool_materials"));

    // Netherite's mining level/speed/attack damage bonus/enchantability, but diamond's durability.
    public static final ToolMaterial SAPPHIRE = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 1561, 9.0F, 4.0F, 15, SAPPHIRE_TOOL_MATERIALS);
}
