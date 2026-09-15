package codyhuh.onceuponatime.registry;

import codyhuh.onceuponatime.OnceUponATime;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.UpwardsBranchingTrunkPlacer;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> BERRY_TREE = createKey("berry_tree");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<Block> holder = context.lookup(Registries.BLOCK);

        register(context, BERRY_TREE, Feature.TREE,
                tree((
                new UpwardsBranchingTrunkPlacer(8, 2, 2, ConstantInt.of(3), 0.5F, ConstantInt.of(3), holder.getOrThrow(ModTags.Blocks.BERRY_TREE_CAN_GROW_THROUGH))),
                new BlobFoliagePlacer(UniformInt.of(2, 3), UniformInt.of(1, 2), 1),
                Blocks.OAK_LOG,
                Blocks.OAK_LEAVES,
                new TwoLayersFeatureSize(2, 1, 2)));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(OnceUponATime.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC config) {
        context.register(key, new ConfiguredFeature<>(feature, config));
    }

    private static TreeConfiguration tree(TrunkPlacer trunkPlacer, FoliagePlacer foliagePlacer, Block log, Block leaves, FeatureSize featureSize) {
        return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), trunkPlacer,
                BlockStateProvider.simple(leaves),
                foliagePlacer, featureSize).ignoreVines().decorators(ImmutableList.of(new LeaveVineDecorator(0.5F))).build();
    }
}