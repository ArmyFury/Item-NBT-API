package dev.tr7zw.nbtapi.plugin.tests.legacy.blocks;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;

import de.tr7zw.nbtapi.NBTBlock;
import de.tr7zw.nbtapi.utils.MinecraftVersion;
import dev.tr7zw.nbtapi.NbtApiException;
import dev.tr7zw.nbtapi.plugin.tests.Test;

public class BlockNBTTest implements Test {

	@Override
	public void test() throws Exception {
		if(MinecraftVersion.getVersion().getVersionId() < MinecraftVersion.MC1_16_R3.getVersionId())return;
		if (!Bukkit.getWorlds().isEmpty()) {
			World world = Bukkit.getWorlds().get(0);
			try {
				if (world.getLoadedChunks().length > 1) {
					Chunk chunk = world.getLoadedChunks()[0];
					Block block = chunk.getBlock(0, 254, 0);
					NBTBlock comp = new NBTBlock(block);
					comp.getData().removeKey("Too");
					if(comp.getData().hasKey("Too")) {
						throw new NbtApiException("Unable to remove key from Block!");
					}
					comp.getData().setString("Too", "Bar");
					if (!new NBTBlock(block).getData().getString("Too").equals("Bar")) {
						throw new NbtApiException("Custom Data did not save to a Block!");
					}
				}
			} catch (Exception ex) {
				throw new NbtApiException("Wasn't able to use NBTBlocks!", ex);
			}
		}
	}

}