package codyhuh.onceuponatime.registry;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.common.items.DyeableHippogryphArmorItem;
import codyhuh.onceuponatime.common.items.HippogryphArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OnceUponATime.MOD_ID);

    public static final RegistryObject<Item> HIPPOGRYPH_SPAWN_EGG = ITEMS.register("hippogryph_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.HIPPOGRYPH, 0x643c23, 0xdad7cf, new Item.Properties()));
    public static final RegistryObject<Item> UNICORN_SPAWN_EGG = ITEMS.register("unicorn_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.UNICORN, 0xfff2d1, 0xf4aa8d, new Item.Properties()));
    public static final RegistryObject<Item> HYDRA_SPAWN_EGG = ITEMS.register("hydra_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.HYDRA, 0x24693d, 0xe6e73c, new Item.Properties()));
    public static final RegistryObject<Item> WYVERN_SPAWN_EGG = ITEMS.register("wyvern_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.WYVERN, 0x732e35, 0x412326, new Item.Properties()));

    public static final RegistryObject<Item> IRON_HIPPOGRYPH_ARMOR = ITEMS.register("iron_hippogryph_armor", () -> new HippogryphArmorItem(5, "iron", new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GOLDEN_HIPPOGRYPH_ARMOR = ITEMS.register("golden_hippogryph_armor", () -> new HippogryphArmorItem(7, "gold", new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> DIAMOND_HIPPOGRYPH_ARMOR = ITEMS.register("diamond_hippogryph_armor", () -> new HippogryphArmorItem(11, "diamond", new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> LEATHER_HIPPOGRYPH_ARMOR = ITEMS.register("leather_hippogryph_armor", () -> new DyeableHippogryphArmorItem(3, "leather", new Item.Properties().stacksTo(1)));
}
