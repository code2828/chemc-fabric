package com.code2828.mods;

import java.util.function.Supplier;

import com.code2828.mods._Tools._AxeItem;
import com.code2828.mods._Tools._HoeItem;
import com.code2828.mods._Tools._PickaxeItem;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.Lazy;
import net.minecraft.util.registry.Registry;

public class CheMC implements ModInitializer {
    // 新物品的实例
    public enum MetalMaterials implements ToolMaterial {
        SOFT_METAL(1, 24, 10, 1.1F, 25, () -> {
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

    public static final Item Li_INGOT = new Item(new FabricItemSettings().group(ItemGroup.MISC));
    public static final SwordItem Li_SWORD = new SwordItem(MetalMaterials.SOFT_METAL, 4, 2,
            new FabricItemSettings().group(ItemGroup.COMBAT).maxDamageIfAbsent(24));
    public static final _PickaxeItem Li_PICKAXE = new _PickaxeItem(MetalMaterials.SOFT_METAL, 3, 2,
            new FabricItemSettings().group(ItemGroup.TOOLS).maxDamageIfAbsent(24));
    public static final _AxeItem Li_AXE = new _AxeItem(MetalMaterials.SOFT_METAL, 5, 1,
            new FabricItemSettings().group(ItemGroup.TOOLS).maxDamageIfAbsent(24));
    public static final _HoeItem Li_HOE = new _HoeItem(MetalMaterials.SOFT_METAL, 1, 2.5F,
            new FabricItemSettings().group(ItemGroup.TOOLS).maxDamageIfAbsent(24));
    public static final ShovelItem Li_SHOVEL = new ShovelItem(MetalMaterials.SOFT_METAL, 4, 2,
            new FabricItemSettings().group(ItemGroup.COMBAT).maxDamageIfAbsent(24));

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("chemc", "lithium_ingot"), Li_INGOT);
        Registry.register(Registry.ITEM, new Identifier("chemc", "lithium_sword"), Li_SWORD);
        Registry.register(Registry.ITEM, new Identifier("chemc", "lithium_pickaxe"), Li_PICKAXE);
        Registry.register(Registry.ITEM, new Identifier("chemc", "lithium_hoe"), Li_HOE);
        Registry.register(Registry.ITEM, new Identifier("chemc", "lithium_axe"), Li_AXE);
        Registry.register(Registry.ITEM, new Identifier("chemc", "lithium_shovel"), Li_SHOVEL);
    }
}