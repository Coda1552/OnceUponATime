package codyhuh.onceuponatime.common.level.structure;

import codyhuh.onceuponatime.common.level.structure.piece.BowlStructurePiece;
import codyhuh.onceuponatime.registry.ModBiomes;
import codyhuh.onceuponatime.registry.ModStructures;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class BowlStructure extends AbstractCaveGenerationStructure {
    private static final int BOWL_WIDTH_RADIUS = 100;
    private static final int BOWL_HEIGHT_RADIUS = 80;

    public static final int BOWL_Y_CENTER = -1;

    public static final Codec<BowlStructure> CODEC = simpleCodec((settings) -> new BowlStructure(settings));

    public BowlStructure(StructureSettings settings) {
        super(settings, ModBiomes.SERPENTS_COVE);
    }

    @Override
    protected StructurePiece createPiece(BlockPos offset, BlockPos center, int heightBlocks, int widthBlocks, RandomState randomState) {
        return new BowlStructurePiece(offset, center, heightBlocks, widthBlocks);
    }

    @Override
    public int getGenerateYHeight(WorldgenRandom random, int x, int y) {
        return BOWL_Y_CENTER;
    }

    @Override
    public int getWidthRadius(WorldgenRandom random) {
        return BOWL_WIDTH_RADIUS;
    }

    @Override
    public int getHeightRadius(WorldgenRandom random, int seaLevel) {
        return BOWL_HEIGHT_RADIUS;
    }

    @Override
    public StructureType<?> type() {
        return ModStructures.BOWL.get();
    }
}