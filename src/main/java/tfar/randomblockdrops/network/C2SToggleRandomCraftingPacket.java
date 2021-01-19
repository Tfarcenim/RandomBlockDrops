package tfar.randomblockdrops.network;

import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import tfar.randomblockdrops.RandomBlockDrops;

import java.util.function.Supplier;

public class C2SToggleRandomCraftingPacket {

  public C2SToggleRandomCraftingPacket() {
  }

  public void handle(Supplier<NetworkEvent.Context> ctx) {
    boolean original = RandomBlockDrops.ServerConfig.randomToggle.get();
    RandomBlockDrops.ServerConfig.randomToggle.set(!RandomBlockDrops.ServerConfig.randomToggle.get());
    ctx.get().getSender().getServer().getPlayerList().func_232641_a_(new StringTextComponent("Changing random block drops to: "+!original), ChatType.CHAT, Util.DUMMY_UUID);
  }
}