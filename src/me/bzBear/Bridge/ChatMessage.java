package me.bzBear.Bridge;

import org.bukkit.ChatColor;

public class ChatMessage {
	public static void scored(BridgePlayer e) {
		ChatColor c;
		if(e.isBlue()) {
			c = ChatColor.BLUE;
			
		}
		else {
			c = ChatColor.RED;
		}
		
		e.bp.sendMessage(yellowLine() + "\n");
		
		e.bp.sendMessage(c + e.bp.getDisplayName() + 
				ChatColor.YELLOW + "(" + e.bp.getHealth() + "❤)"+ 
				ChatColor.WHITE + " Scored" + 
				ChatColor.YELLOW + "(Goal " + e.goals + ")");
		
		e.bp.sendMessage("\n");
		e.bp.sendMessage(yellowLine());
	}
	
	

	public static void finish(BridgePlayer e, Game game, BridgePlayer finisher) {
		
		
		e.bp.sendMessage(yellowLine());
		
		
		if(finisher.isBlue()) {
			e.bp.sendMessage(ChatColor.BLUE + "    BLUE WON THE MATCH");
			
		}
		else {
			e.bp.sendMessage(ChatColor.RED + "    RED WON THE MATCH");
		}
		
		e.bp.sendMessage(yellowLine());
		
	}
	
	
	public static String yellowLine() {
		return ChatColor.YELLOW + "------------------------------";
	}
}
