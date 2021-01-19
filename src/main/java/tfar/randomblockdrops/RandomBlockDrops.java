package tfar.randomblockdrops;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.commons.lang3.tuple.Pair;
import tfar.randomblockdrops.network.PacketHandler;

import java.util.*;
import java.util.stream.IntStream;

@Mod(RandomBlockDrops.MODID)
@Mod.EventBusSubscriber
public class RandomBlockDrops {
    public static final String MODID = "randomblockdrops";
    public static final String NAME = "Random Block Drops";
    public static final String VERSION = "1.1";

    public static Map<Block, Block> map = new HashMap<>();

    public RandomBlockDrops() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_SPEC);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::init);
        bus.addListener(this::common);
        if (FMLEnvironment.dist.isClient()) {
            MinecraftForge.EVENT_BUS.addListener(Client::keyPress);
            bus.addListener(Client::keybind);
        }
    }

    private void common(FMLCommonSetupEvent e) {
        PacketHandler.registerMessages(MODID);
    }

    public static final ServerConfig SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;

    static {
        final Pair<ServerConfig, ForgeConfigSpec> specPair2 = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        SERVER_SPEC = specPair2.getRight();
        SERVER = specPair2.getLeft();
    }

    public void init(FMLCommonSetupEvent event) {
        List<Block> firstList = new ArrayList<>();

        for (Block block : Registry.BLOCK) {
            firstList.add(block);
        }

        List<Block> secondList = new ArrayList<>(firstList);
        Collections.shuffle(secondList);

        IntStream.range(0, firstList.size()).forEach(i -> map.put(firstList.get(i), secondList.get(i)));
    }

    public static class ServerConfig {

        public static ForgeConfigSpec.BooleanValue randomToggle;

        public ServerConfig(ForgeConfigSpec.Builder builder) {
            builder.push("server");
            randomToggle = builder.
                    comment("controls whether or not random block drops are active")
                    .define("active", true);
            builder.pop();
        }
    }
}
