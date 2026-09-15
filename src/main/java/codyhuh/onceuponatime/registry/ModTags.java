package codyhuh.onceuponatime.registry;

import codyhuh.onceuponatime.OnceUponATime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static class Blocks {
        public static final TagKey<Block> BERRY_TREE_CAN_GROW_THROUGH = tag("berry_tree_can_grow_through");

        private static TagKey<Block> tag(String name){
            return BlockTags.create(new ResourceLocation(OnceUponATime.MOD_ID, name));
        }
    }

}
