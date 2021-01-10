package tfar.randomblockdrops;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;
import java.util.stream.IntStream;

@Mod(modid = RandomBlockDrops.MODID, name = RandomBlockDrops.NAME, version = RandomBlockDrops.VERSION)
@Mod.EventBusSubscriber
public class RandomBlockDrops {
    public static final String MODID = "randomblockdrops";
    public static final String NAME = "Random Block Drops";
    public static final String VERSION = "1.0";

    public static Map<Block, Block> map = new HashMap<>();

    @EventHandler
    public void init(FMLInitializationEvent event) {
        List<Block> firstList = new ArrayList<>();

        for (Block block : Block.REGISTRY) {
            firstList.add(block);
        }

        List<Block> secondList = new ArrayList<>(firstList);
        Collections.shuffle(secondList);

        IntStream.range(0, firstList.size()).forEach(i -> map.put(firstList.get(i), secondList.get(i)));
    }

    @SubscribeEvent
    public static void drops(BlockEvent.HarvestDropsEvent e) {
        Block originalBlock = e.getState().getBlock();
        Block newBlock = map.get(originalBlock);
        List<ItemStack> drops = e.getDrops();
        drops.clear();
        NonNullList<ItemStack> newDrops = NonNullList.create();
        newBlock.getDrops(newDrops,e.getWorld(),e.getPos(),newBlock.getDefaultState(),e.getFortuneLevel());
        drops.addAll(newDrops);
    }
}
