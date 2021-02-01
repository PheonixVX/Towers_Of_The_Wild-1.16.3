package io.github.ender.towersofthewild.structures;

import com.mojang.serialization.Codec;
import io.github.ender.towersofthewild.config.TowersOfTheWildConfig;
import io.github.ender.towersofthewild.register.TowerStructuresRegistry;
import io.github.ender.towersofthewild.structures.pieces.DerelictTowerPools;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.minecraft.structure.MarginedStructureStart;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

import java.util.Objects;

public class DerelictTowerStructure extends AbstractTowerStructure {
	public DerelictTowerStructure(Codec<StructurePoolFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public int getSeedModifier() {
		return 1689780;
	}

	@Override
	public int getSize() {
		return 18;
	}

	@Override
	public int getDistance() {
		TowersOfTheWildConfig config = AutoConfig.getConfigHolder(TowersOfTheWildConfig.class).getConfig();
		return config.derelictRarity;
	}

	@Override
	public int getSeparation() {
		TowersOfTheWildConfig config = AutoConfig.getConfigHolder(TowersOfTheWildConfig.class).getConfig();
		return config.derelictRarity / 3;
	}

	@Override
	protected boolean isTerrainFlat(ChunkGenerator generator, int chunkX, int chunkZ) {

		int offset = getSize();

		int xStart = chunkX * 16;
		int zStart = chunkZ * 16;

		int i1 = generator.getHeight(xStart, zStart, Heightmap.Type.WORLD_SURFACE_WG);
		int j1 = generator.getHeight(xStart, zStart + offset, Heightmap.Type.WORLD_SURFACE_WG);
		int k1 = generator.getHeight(xStart + offset, zStart, Heightmap.Type.WORLD_SURFACE_WG);
		int l1 = generator.getHeight(xStart + offset, zStart + offset, Heightmap.Type.WORLD_SURFACE_WG);
		int m1 = generator.getHeight(xStart + offset / 2, zStart + offset / 2, Heightmap.Type.WORLD_SURFACE_WG);
		int minHeight = Math.min(Math.min(Math.min(i1, j1), Math.min(k1, l1)), m1);
		int maxHeight = Math.max(Math.max(Math.max(i1, j1), Math.max(k1, l1)), m1);

		return Math.abs(maxHeight - minHeight) <= 4;
	}

	@Override
	protected boolean alreadyIsTower(ChunkGenerator generator, AbstractTowerStructure structure, long seed, ChunkRandom rand, int chunkX, int chunkZ) {
		TowersOfTheWildConfig config = AutoConfig.getConfigHolder(TowersOfTheWildConfig.class).getConfig();
		for (int k = chunkX - config.rarity / 6; k <= chunkX + config.rarity / 6; ++k) {
			for (int l = chunkZ - config.rarity / 6; l <= chunkZ + config.rarity / 6; ++l) {
				for (StructureFeature<?> ro : TowerStructuresRegistry.STRUCTURE_FEATURES) {
					if (!structure.equals(ro)) {
						ChunkPos otherStructurePos = ro.getStartChunk(Objects.requireNonNull(generator.getStructuresConfig().getForType(ro)), seed, rand, k, l);
						if (k == otherStructurePos.x && l == otherStructurePos.z) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public StructureStartFactory<StructurePoolFeatureConfig> getStructureStartFactory() {
		return DerelictTowerStructure.Start::new;
	}

	public static class Start extends MarginedStructureStart<StructurePoolFeatureConfig> {
		public Start(StructureFeature<StructurePoolFeatureConfig> structure, int p_i225876_2_, int p_i225876_3_, BlockBox boundingBox, int p_i225876_5_, long p_i225876_6_) {
			super(structure, p_i225876_2_, p_i225876_3_, boundingBox, p_i225876_5_, p_i225876_6_);
		}

		// generate
		public void init(DynamicRegistryManager registryManager, ChunkGenerator generator, StructureManager manager, int p_230364_4_, int p_230364_5_, Biome p_230364_6_, StructurePoolFeatureConfig villageConfig) {
			int i = p_230364_4_ * 16;
			int j = p_230364_5_ * 16;
			if (!DerelictTowerPools.registered) {
				DerelictTowerPools.init(registryManager);
			}
			BlockPos blockpos = new BlockPos(i, 0, j);
			StructurePoolBasedGenerator.method_30419(registryManager, villageConfig, PoolStructurePiece::new, generator, manager, blockpos, this.children, this.random, true, true);
			this.setBoundingBoxFromChildren();

		}
	}
}
