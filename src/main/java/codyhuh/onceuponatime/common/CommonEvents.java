package codyhuh.onceuponatime.common;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.common.entities.Hippogryph;
import codyhuh.onceuponatime.common.entities.Hydra;
import codyhuh.onceuponatime.common.entities.Unicorn;
import codyhuh.onceuponatime.common.entities.Wyvern;
import codyhuh.onceuponatime.registry.ModEntities;
import codyhuh.onceuponatime.registry.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = OnceUponATime.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {

    @SubscribeEvent
    public static void createAttributes(EntityAttributeCreationEvent e) {
        e.put(ModEntities.HIPPOGRYPH.get(), Hippogryph.createHippogryphAttributes().build());
        e.put(ModEntities.UNICORN.get(), Unicorn.createUnicornAttributes().build());
        e.put(ModEntities.HYDRA.get(), Hydra.createHydraAttributes().build());
        e.put(ModEntities.WYVERN.get(), Wyvern.createWyvernAttributes().build());
    }
}