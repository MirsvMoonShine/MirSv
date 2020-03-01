package com.mirsv.util;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.mirsv.Mirsv;
import org.bukkit.entity.Player;

public class BungeeUtil {

	private BungeeUtil() {}

	public static void connect(Player player, String channel) {
		ByteArrayDataOutput output = ByteStreams.newDataOutput(2);
		output.writeUTF("Connect");
		output.writeUTF(channel);
		player.sendPluginMessage(Mirsv.getPlugin(), "BungeeCord", output.toByteArray());
	}

}
