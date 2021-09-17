package com.code2828.mods;

import com.code2828.mods._Tools.*;
import java.util.function.Supplier;
import net.fabricmc.api.ModInitializer;
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
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class CheMC implements ModInitializer {

    public enum MetalMaterials implements ToolMaterial {
        // prettier-ignore-start
            SOFT_METAL(
                1, 24, 10, 0.1F, 25, () -> {
                    return Ingredient.ofItems(new ItemConvertible[] { CheMC.Li_INGOT });
                }
            ),
            
            COMMON_METAL(
                1, 500, 7, 1, 17, () -> {
                    return Ingredient.ofItems(new ItemConvertible[] { CheMC.Zn_INGOT });
                }
            );

        // prettier-ignore-end
            
            private final int miningLevel;
        private final int itemDurability;
        private final float miningSpeed;
        private final float attackDamage;
        private final int enchantability;
        private final Lazy<Ingredient> repairIngredient;

        private MetalMaterials(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
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
    public static final SwordItem Li_SWORD = new SwordItem(MetalMaterials.SOFT_METAL, 3, 0.2F, new FabricItemSettings().group(ItemGroup.COMBAT).maxDamageIfAbsent(24));
    public static final _PickaxeItem Li_PICKAXE = new _PickaxeItem(MetalMaterials.SOFT_METAL, 2, 0.15F, new FabricItemSettings().group(ItemGroup.TOOLS).maxDamageIfAbsent(24));
    public static final _PickaxeItem Zn_PICKAXE = new _PickaxeItem(MetalMaterials.COMMON_METAL, 4, 0.08F, new FabricItemSettings().group(ItemGroup.TOOLS).maxDamageIfAbsent(500));
    public static final _AxeItem Li_AXE = new _AxeItem(MetalMaterials.SOFT_METAL, 4, 0.1F, new FabricItemSettings().group(ItemGroup.TOOLS).maxDamageIfAbsent(24));
    public static final _HoeItem Li_HOE = new _HoeItem(MetalMaterials.SOFT_METAL, 1, 0.6F, new FabricItemSettings().group(ItemGroup.TOOLS).maxDamageIfAbsent(24));
    public static final ShovelItem Li_SHOVEL = new ShovelItem(MetalMaterials.SOFT_METAL, 1, 0.22F, new FabricItemSettings().group(ItemGroup.TOOLS).maxDamageIfAbsent(24));
    public static final Item H2_GLASS = new Item(new FabricItemSettings().group(ItemGroup.MISC));
    public static final Item HCl_GLASS = new Item(new FabricItemSettings().group(ItemGroup.MISC));
    public static final Item N2_GLASS = new Item(new FabricItemSettings().group(ItemGroup.MISC));
    public static final Item O2_GLASS = new Item(new FabricItemSettings().group(ItemGroup.MISC));
    public static final Item Cl2_GLASS = new Item(new FabricItemSettings().group(ItemGroup.MISC));
    public static final Item spodumene_DUST = new Item(new FabricItemSettings().group(ItemGroup.MISC)); // LiAl[Si2O6], crafted with 1 nugget or by smelting a granite
    public static final Item spodumene_NUGGET = new Item(new FabricItemSettings().group(ItemGroup.MISC)); // LiAl[Si2O6], dropped by an ore or crafted with 9 dusts
    public static final Item Zn_INGOT = new Item(new FabricItemSettings().group(ItemGroup.MISC));
    public static final Item sphalerite_NUGGET = new Item(new FabricItemSettings().group(ItemGroup.MISC)); //ZnS, dropped by an ore
    public static final Item lime_DUST = new Item(new FabricItemSettings().group(ItemGroup.MISC)); // CaO, blasted from a stone (not cobblestone)
    public static final Item hydrated_lime_DUST = new Item(new FabricItemSettings().group(ItemGroup.MISC)); // Ca(OH)2

    // hardness is from baike.baidu.com
    public static final Block Li_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(h2h(0.6)));
    /* LiAl[Si2O6], drops 7 nuggets
     * method: Li2O·Al2O3·4SiO2+8CaO→Li2O·Al2O3+4Ca2SiO4
     *         Li2O·Al2O3+Ca(OH)2→2LiOH+CaO·Al2O3
     * Need to add CaO and Ca(OH)2!
     */
    public static final Block spodumene_ORE = new Block(FabricBlockSettings.of(Material.STONE).strength(h2h(6.8)));
    public static final Block Zn_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(h2h(2.5)));
    public static final Block sphalerite_ORE = new Block(FabricBlockSettings.of(Material.STONE).strength(h2h(3.5))); // ZnS, drops 7 sphalerite nuggets

    public static final ConfiguredFeature<?, ?> ORE_SPODUMENE_Ov = Feature.ORE
        .configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, spodumene_ORE.getDefaultState(), 2))
        .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(0, 0, 128)))
        .spreadHorizontally()
        .repeat(26); // number of veins per chunk

    public static float h2h(double realHardness) { // returns: float minecraftHardness
        return (float) Math.pow((Math.E / 2.0) + 0.05, realHardness);
    }

    public void registerItem(String identifier, Item item_) {
        Registry.register(Registry.ITEM, new Identifier("chemc", identifier), item_);
    }

    public void registerBABI(String identifier, Block block_, ItemGroup gruop) { // Block And BlockItem; intended typo
        Registry.register(Registry.BLOCK, new Identifier("chemc", identifier), block_);
        Registry.register(Registry.ITEM, new Identifier("chemc", identifier), new BlockItem(block_, new FabricItemSettings().group(gruop)));
    }

    @Override
    public void onInitialize() {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier("chemc", "ore_spodumene_ov"), ORE_SPODUMENE_Ov);
        // Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier("tutorial", "ore_wool_overworld"), ORE_WOOL_OVERWORLD);
        registerItem("lithium_ingot", Li_INGOT);
        registerItem("lithium_sword", Li_SWORD);
        registerItem("lithium_pickaxe", Li_PICKAXE);
        registerItem("zinc_pickaxe",Zn_PICKAXE);
        registerItem("lithium_hoe", Li_HOE);
        registerItem("lithium_axe", Li_AXE);
        registerItem("lithium_shovel", Li_SHOVEL);
        registerItem("hydrogen", H2_GLASS);
        registerItem("oxygen", O2_GLASS);
        registerItem("nitrogen", N2_GLASS);
        registerItem("hydrogen_chloride", HCl_GLASS);
        registerItem("chlorine", Cl2_GLASS);
        registerItem("spodumene_dust", spodumene_DUST);
        registerItem("spodumene_nugget", spodumene_NUGGET);
        registerItem("zinc_ingot", Zn_INGOT);
        registerItem("sphalerite_nugget", sphalerite_NUGGET);
        registerItem("lime_dust", lime_DUST);
        registerItem("hydrated_lime_dust", hydrated_lime_DUST);

        registerBABI("spodumene", spodumene_ORE, ItemGroup.BUILDING_BLOCKS);
        registerBABI("lithium_block", Li_BLOCK, ItemGroup.BUILDING_BLOCKS);
        registerBABI("sphalerite", sphalerite_ORE, ItemGroup.BUILDING_BLOCKS);
        registerBABI("zinc_block", Zn_BLOCK, ItemGroup.BUILDING_BLOCKS);
    }
}
