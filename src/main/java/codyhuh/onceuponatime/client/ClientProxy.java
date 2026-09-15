package codyhuh.onceuponatime.client;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.common.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = OnceUponATime.MOD_ID, value = Dist.CLIENT)
public class ClientProxy extends CommonProxy {
    public static List<UUID> blockedEntityRenders = new ArrayList<>();

    public void clientInit() {
        //MinecraftForge.EVENT_BUS.register(new ClientForgeEvents());
    }

    public Player getClientSidePlayer() {
        return Minecraft.getInstance().player;
    }

    public void blockRenderingEntity(UUID id) {
        blockedEntityRenders.add(id);
    }

    public void releaseRenderingEntity(UUID id) {
        blockedEntityRenders.remove(id);
    }
}
