package codyhuh.onceuponatime.client;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.client.models.HippogryphModel;
import codyhuh.onceuponatime.client.renders.HippogryphRenderer;
import codyhuh.onceuponatime.common.entities.Hippogryph;
import codyhuh.onceuponatime.registry.ModEntities;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.event.KeyEvent;

@Mod.EventBusSubscriber(modid = OnceUponATime.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {

    @SubscribeEvent
    public static void registerRenders(EntityRenderersEvent.RegisterRenderers e) {
        e.registerEntityRenderer(ModEntities.HIPPOGRYPH.get(), HippogryphRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions e) {
        e.registerLayerDefinition(HippogryphModel.LAYER_LOCATION, HippogryphModel::createBodyLayer);
    }

    public static KeyMapping descendKey;

    @SubscribeEvent
    public static void registerKeyMappings(final RegisterKeyMappingsEvent event) {
        descendKey = create("flightDescend", KeyEvent.VK_X);

        event.register(descendKey);
    }

    private static KeyMapping create(String name, int key) {
        return new KeyMapping("key." + OnceUponATime.MOD_ID + "." + name, key, "key.category." + OnceUponATime.MOD_ID);
    }

    @Mod.EventBusSubscriber(modid = OnceUponATime.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void cameraOverlay(ViewportEvent.ComputeCameraAngles e) {
            Minecraft mc = Minecraft.getInstance();
            var player = mc.player;

            if (mc.options.getCameraType().isFirstPerson() && player != null && player.isPassenger() && player.getVehicle() instanceof Hippogryph hippogryph) {
                float factor = 1.0F;

                float pitch = e.getPitch() + (hippogryph.getXRot() * factor);
                float roll = e.getRoll() + (hippogryph.tilt * factor);

                e.setPitch(pitch);
                e.setRoll(roll);
            }
        }
    }
}
