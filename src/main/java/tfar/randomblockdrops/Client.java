package tfar.randomblockdrops;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;
import tfar.randomblockdrops.network.C2SToggleRandomCraftingPacket;
import tfar.randomblockdrops.network.PacketHandler;

public class Client {

    public static KeyBinding TOGGLE;

    public static void keybind(FMLClientSetupEvent e) {
        TOGGLE = new KeyBinding("toggle_drops", GLFW.GLFW_KEY_U, RandomBlockDrops.MODID);
        ClientRegistry.registerKeyBinding(TOGGLE);
    }

    public static void keyPress(InputEvent.KeyInputEvent e) {
        while (TOGGLE.isPressed()) {
            PacketHandler.INSTANCE.sendToServer(new C2SToggleRandomCraftingPacket());
        }
    }
}