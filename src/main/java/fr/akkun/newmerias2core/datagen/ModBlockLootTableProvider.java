package fr.akkun.newmerias2core.datagen;

import fr.akkun.newmerias2core.block.ModBlocks;
import fr.akkun.newmerias2core.item.ModItems;
import net.minecraft.advancements.predicates.LocationPredicate;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.BeetrootBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    // Same values vanilla uses for jungle leaves/saplings (BlockLootSubProvider's own NORMAL_LEAVES_SAPLING_CHANCES
    // covers every other species, but jungle has its own lower chances and that array is private in vanilla).
    private static final float[] JUNGLE_LEAVES_SAPLING_CHANCES = {0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};
    private static final List<Block> LEAVES = List.of(
            Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES, Blocks.BIRCH_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.ACACIA_LEAVES,
            Blocks.DARK_OAK_LEAVES, Blocks.PALE_OAK_LEAVES, Blocks.CHERRY_LEAVES, Blocks.AZALEA_LEAVES,
            Blocks.FLOWERING_AZALEA_LEAVES, Blocks.MANGROVE_LEAVES
    );

    public ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        add(ModBlocks.RICE_CROP.get(), createCropDrops(ModBlocks.RICE_CROP.get(),
                ModItems.RICE_SHOOT.get(), ModItems.RICE_SHOOT.get(), LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.RICE_CROP.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7))));

        add(ModBlocks.CHILI_CROP.get(), createCropDrops(ModBlocks.CHILI_CROP.get(),
                ModItems.CHILI_PEPPER.get(), ModItems.CHILI_SEEDS.get(), LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.CHILI_CROP.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BeetrootBlock.AGE, 3))));

        addPeerDropsToAllLeaves();

        add(ModBlocks.SAPPHIRE_ORE.get(), createOreDrop(ModBlocks.SAPPHIRE_ORE.get(), ModItems.SAPPHIRE.get()));
        add(ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get(), createOreDrop(ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get(), ModItems.SAPPHIRE.get()));
        dropSelf(ModBlocks.SAPPHIRE_BLOCK.get());

        List<Block> selfDropping = List.of(
                ModBlocks.BLACK_SAND.get(), ModBlocks.BLACK_SANDSTONE.get(), ModBlocks.CHISELED_BLACK_SANDSTONE.get(),
                ModBlocks.CUT_BLACK_SANDSTONE.get(), ModBlocks.SMOOTH_BLACK_SANDSTONE.get(), ModBlocks.BLACK_SANDSTONE_STAIRS.get(),
                ModBlocks.SMOOTH_BLACK_SANDSTONE_STAIRS.get(), ModBlocks.BLACK_SANDSTONE_WALL.get(),
                ModBlocks.COBBLED_MARBLE.get(), ModBlocks.MARBLE_STAIRS.get(), ModBlocks.MARBLE_BRICKS.get(),
                ModBlocks.CHISELED_MARBLE_BRICKS.get(), ModBlocks.CRACKED_MARBLE_BRICKS.get(), ModBlocks.MARBLE_BRICK_STAIRS.get(),
                ModBlocks.MARBLE_BRICK_WALL.get(), ModBlocks.MOSSY_MARBLE_BRICKS.get(), ModBlocks.MOSSY_MARBLE_BRICK_STAIRS.get(),
                ModBlocks.MOSSY_MARBLE_BRICK_WALL.get(), ModBlocks.COBBLED_MARBLE_STAIRS.get(), ModBlocks.COBBLED_MARBLE_WALL.get(),
                ModBlocks.MARBLE_PRESSURE_PLATE.get(), ModBlocks.MARBLE_BUTTON.get()
        );
        selfDropping.forEach(this::dropSelf);

        add(ModBlocks.MARBLE.get(), createSingleItemTableWithSilkTouch(ModBlocks.MARBLE.get(), ModBlocks.COBBLED_MARBLE.get()));

        List<Block> slabs = List.of(
                ModBlocks.BLACK_SANDSTONE_SLAB.get(), ModBlocks.CUT_BLACK_SANDSTONE_SLAB.get(), ModBlocks.SMOOTH_BLACK_SANDSTONE_SLAB.get(),
                ModBlocks.MARBLE_SLAB.get(), ModBlocks.MARBLE_BRICK_SLAB.get(), ModBlocks.MOSSY_MARBLE_BRICK_SLAB.get(),
                ModBlocks.COBBLED_MARBLE_SLAB.get()
        );
        slabs.forEach(slab -> add(slab, createSlabItemTable(slab)));
    }

    /** Every leaf type keeps its normal drops (sticks, sapling, and oak/dark oak's apple), plus a peer drop -
     *  at the same odds as apples on oak - but only when broken while standing in a birch forest biome. */
    private void addPeerDropsToAllLeaves() {
        HolderLookup.RegistryLookup<Enchantment> enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        Holder<Biome> birchForest = this.registries.lookupOrThrow(Registries.BIOME).getOrThrow(Biomes.BIRCH_FOREST);
        LootItemCondition.Builder inBirchForest = LocationCheck.checkLocation(LocationPredicate.Builder.inBiome(birchForest));

        for (Block leaves : LEAVES) {
            add(leaves, withPeerDrop(baseLeavesDrops(leaves), leaves, inBirchForest, enchantments));
        }
    }

    private LootTable.Builder baseLeavesDrops(Block leaves) {
        if (leaves == Blocks.OAK_LEAVES) {
            return createOakLeavesDrops(leaves, Blocks.OAK_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES);
        }
        if (leaves == Blocks.DARK_OAK_LEAVES) {
            return createOakLeavesDrops(leaves, Blocks.DARK_OAK_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES);
        }
        if (leaves == Blocks.SPRUCE_LEAVES) {
            return createLeavesDrops(leaves, Blocks.SPRUCE_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES);
        }
        if (leaves == Blocks.BIRCH_LEAVES) {
            return createLeavesDrops(leaves, Blocks.BIRCH_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES);
        }
        if (leaves == Blocks.JUNGLE_LEAVES) {
            return createLeavesDrops(leaves, Blocks.JUNGLE_SAPLING, JUNGLE_LEAVES_SAPLING_CHANCES);
        }
        if (leaves == Blocks.ACACIA_LEAVES) {
            return createLeavesDrops(leaves, Blocks.ACACIA_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES);
        }
        if (leaves == Blocks.PALE_OAK_LEAVES) {
            return createLeavesDrops(leaves, Blocks.PALE_OAK_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES);
        }
        if (leaves == Blocks.CHERRY_LEAVES) {
            return createLeavesDrops(leaves, Blocks.CHERRY_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES);
        }
        if (leaves == Blocks.AZALEA_LEAVES) {
            return createLeavesDrops(leaves, Blocks.AZALEA, NORMAL_LEAVES_SAPLING_CHANCES);
        }
        if (leaves == Blocks.FLOWERING_AZALEA_LEAVES) {
            return createLeavesDrops(leaves, Blocks.FLOWERING_AZALEA, NORMAL_LEAVES_SAPLING_CHANCES);
        }
        if (leaves == Blocks.MANGROVE_LEAVES) {
            return createMangroveLeavesDrops(leaves);
        }
        throw new IllegalArgumentException("Unhandled leaves block: " + leaves);
    }

    private LootTable.Builder withPeerDrop(LootTable.Builder builder, Block leaves, LootItemCondition.Builder inBirchForest,
                                            HolderLookup.RegistryLookup<Enchantment> enchantments) {
        return builder.withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .when(this.hasShears().or(this.hasSilkTouch()).invert())
                .when(inBirchForest)
                .add(((LootPoolSingletonContainer.Builder<?>) this.applyExplosionCondition(leaves, LootItem.lootTableItem(ModItems.PEER.get())))
                        .when(BonusLevelTableCondition.bonusLevelFlatChance(
                                enchantments.getOrThrow(Enchantments.FORTUNE), 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
    }

    protected LootTable.Builder createMultipleOreDrops(Block block, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(block, this.applyExplosionDecay(block,
                LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Stream.concat(ModBlocks.BLOCKS.getEntries().stream().map(Holder::value), LEAVES.stream())::iterator;
    }
}
