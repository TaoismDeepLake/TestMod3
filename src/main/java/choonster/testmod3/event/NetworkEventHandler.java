package choonster.testmod3.event;

import choonster.testmod3.TestMod3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Handler for {@link FMLNetworkEvent} subclasses.
 * <p>
 * WARNING: These events are fired on a Netty thread rather than the main client/server thread, so the handler methods must not directly interact with normal Minecraft classes.
 * <p>
 * Use {@link IThreadListener#addScheduledTask} to run a task on the main thread where you can safely interact with normal Minecraft classes.
 *
 * @author Choonster
 */
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = TestMod3.MODID)
public class NetworkEventHandler {
	private static final Logger LOGGER = LogManager.getLogger();

	/**
	 * Prints server connection info when the client connects to a server.
	 * <p>
	 * Test for this thread:
	 * http://www.minecraftforge.net/forum/index.php/topic,34546.0.html
	 *
	 * @param event The event
	 */
	@SubscribeEvent
	public static void clientConnectedToServer(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
		final IThreadListener mainThread = Minecraft.getMinecraft();
		mainThread.addScheduledTask(() -> {
			final ServerData serverData = Minecraft.getMinecraft().getCurrentServerData();
			LOGGER.info("Server Connected! Local? {} - Address: {}", event.isLocal(), serverData != null ? serverData.serverIP : "<No ServerData>");
		});
	}
}
