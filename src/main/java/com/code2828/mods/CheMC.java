package com.code2828.mods;

import java.util.function.Supplier;

import com.code2828.mods._Tools._AxeItem;
import com.code2828.mods._Tools._HoeItem;
import com.code2828.mods._Tools._PickaxeItem;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.Lazy;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class CheMC implements ModInitializer {
    public enum MetalMaterials implements ToolMaterial {
        SOFT_METAL(1, 24, 10, 0.1F, 25, () -> {
            return Ingredient.ofItems(new ItemConvertible[] { CheMC.Li_INGOT });
        });

        private final int miningLevel;
        private final int itemDurability;
        private final float miningSpeed;
        private final float attackDamage;
        private final int enchantability;
        private final Lazy<Ingredient> repairIngredient;

        private MetalMaterials(int miningLevel, int itemDurability, float miningSpeed, float attackDamage,
                int enchantability, Supplier<Ingredient> repairIngredient) {
            this.miningLevel = miningLevel;
            this.itemDurability = itemDurability;
            this.miningSpeed = miningSpeed;
            this.attackDamage = attackDamage;
            this.enchantability = enchantability;
            this.repairIngredient = new Lazy(repairIngredient);
        }

        public int getDurability() {
            return this.itemDurability;
        }

        public float getMiningSpeedMultiplier() {
            return this.miningSpeed;
        }

        public float getAttackDamage() {
            return this.attackDamage;
        }

        public int getMiningLevel() {
            return this.miningLevel;
        }

        public int getEnchantability() {
            return this.enchantability;
        }

        public Ingredient getRepairIngredient() {
            return (Ingredient) this.repairIngredient.get();
        }
    }

    public static final Item Li_INGOT = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));
    public static final SwordItem Li_SWORD = new SwordItem(MetalMaterials.SOFT_METAL, 3, 0.2F,
            new FabricItemSettings().group(ItemGroup.COMBAT).maxDamageIfAbsent(24));
    public static final _PickaxeItem Li_PICKAXE = new _PickaxeItem(MetalMaterials.SOFT_METAL, 2, 0.15F,
            new FabricItemSettings().group(ItemGroup.TOOLS).maxDamageIfAbsent(24));
    public static final _AxeItem Li_AXE = new _AxeItem(MetalMaterials.SOFT_METAL, 4, 0.1F,
            new FabricItemSettings().group(ItemGroup.TOOLS).maxDamageIfAbsent(24));
    public static final _HoeItem Li_HOE = new _HoeItem(MetalMaterials.SOFT_METAL, 1, 0.6F,
            new FabricItemSettings().group(ItemGroup.TOOLS).maxDamageIfAbsent(24));
    public static final ShovelItem Li_SHOVEL = new ShovelItem(MetalMaterials.SOFT_METAL, 1, 0.22F,
            new FabricItemSettings().group(ItemGroup.TOOLS).maxDamageIfAbsent(24));
    public static final Item H2_GLASS = new Item(new FabricItemSettings().group(ItemGroup.MISC));
    public static final Item HCl_GLASS = new Item(new FabricItemSettings().group(ItemGroup.MISC));
    public static final Item N2_GLASS = new Item(new FabricItemSettings().group(ItemGroup.MISC));
    public static final Item O2_GLASS = new Item(new FabricItemSettings().group(ItemGroup.MISC));
    public static final Item Cl2_GLASS = new Item(new FabricItemSettings().group(ItemGroup.MISC));
    public static final Item LiCl_DUST = new Item(new FabricItemSettings().group(ItemGroup.MISC));

    public static final Block Li_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(1.4f));
    public static final Block LiCl_ORE = new Block(FabricBlockSettings.of(Material.STONE).strength(1.0f));

    private static final ConfiguredFeature<?, ?> ORE_LiCl_O = Feature.ORE
            .configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
                    LiCl_ORE.getDefaultState(), 5)) // vein size
            .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(0,2,128))).spreadHorizontally().repeat(60); // number of veins per chunk

    @Override
    public void onInitialize() {
        RegistryKey<ConfiguredFeature<?, ?>> oreLiClO = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN,
                new Identifier("chemc", "ore_licl_o"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, oreLiClO.getValue(), ORE_LiCl_O);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES,
                oreLiClO);

        Registry.register(Registry.ITEM, new Identifier("chemc", "lithium_ingot"), Li_INGOT);
        Registry.register(Registry.ITEM, new Identifier("chemc", "lithium_sword"), Li_SWORD);
        Registry.register(Registry.ITEM, new Identifier("chemc", "lithium_pickaxe"), Li_PICKAXE);
        Registry.register(Registry.ITEM, new Identifier("chemc", "lithium_hoe"), Li_HOE);
        Registry.register(Registry.ITEM, new Identifier("chemc", "lithium_axe"), Li_AXE);
        Registry.register(Registry.ITEM, new Identifier("chemc", "lithium_shovel"), Li_SHOVEL);
        Registry.register(Registry.ITEM, new Identifier("chemc", "hydrogen"), H2_GLASS);
        Registry.register(Registry.ITEM, new Identifier("chemc", "oxygen"), O2_GLASS);
        Registry.register(Registry.ITEM, new Identifier("chemc", "nitrogen"), N2_GLASS);
        Registry.register(Registry.ITEM, new Identifier("chemc", "hydrogen_chloride"), HCl_GLASS);
        Registry.register(Registry.ITEM, new Identifier("chemc", "chlorine"), Cl2_GLASS);
        Registry.register(Registry.ITEM, new Identifier("chemc", "lithium_chloride"), LiCl_DUST);

        Registry.register(Registry.BLOCK, new Identifier("chemc", "lithium_block"), Li_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier("chemc", "lithium_ore"), LiCl_ORE);

        Registry.register(Registry.ITEM, new Identifier("chemc", "lithium_block"),
                new BlockItem(Li_BLOCK, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier("chemc", "lithium_ore"),
                new BlockItem(LiCl_ORE, new FabricItemSettings().group(ItemGroup.DECORATIONS)));
    }
}