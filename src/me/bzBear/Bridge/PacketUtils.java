package me.bzBear.Bridge;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class PacketUtils {
	public static void sendTitle(Player player, String title, String subtitle, int fadein, int stay, int fadeout) {
		CraftPlayer cp = (CraftPlayer) player;
		PlayerConnection con = cp.getHandle().playerConnection;
		IChatBaseComponent titleJSON = ChatSerializer.a("{'text': '" + title + "'}");
		IChatBaseComponent subtitleJSON = ChatSerializer.a("{'text': '" + subtitle + "'}");
		PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, titleJSON, fadein, stay, fadeout);
		PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, subtitleJSON, fadein, stay, fadeout);
		con.sendPacket(titlePacket);
		con.sendPacket(subtitlePacket);
	}
	
	public static void sendActionBar(Player player, String msg) {
		IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + msg + "\"}");
		PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(ppoc);
	}
}
