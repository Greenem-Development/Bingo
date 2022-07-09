package org.greenem.plugins.bingo.functionality;

import static org.greenem.plugins.bingo.util.SimpleLogger.debug;
import static org.greenem.plugins.bingo.util.SimpleLogger.log;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.greenem.plugins.bingo.config.ConfigData;
import org.greenem.plugins.bingo.data.References;
import org.greenem.plugins.bingo.files.SavingData;
import org.greenem.plugins.bingo.main.Main;
import org.greenem.plugins.bingo.map.GridOperations;
import org.greenem.plugins.bingo.util.StandartUtils;

public class GameOperations {
	public static void start(CommandSender sender, String[] args) {
		if(Bukkit.getOnlinePlayers().size()==0) {
			sender.sendMessage(ChatColor.RED + "There are no players online");
			return;
		}
		if(References.inGame && args.length!=2) {
			sender.sendMessage(ChatColor.RED + "The confirmation required to reset the current game. Add \"" + ChatColor.BOLD + "confirm" + ChatColor.RESET + "" + ChatColor.RED + "\" parameter at the end of the command");
			return;
		}
		if((args.length==1 && !References.inGame) || (args.length==2 && args[1].equalsIgnoreCase("confirm"))) {
			int amount = 0;
			if(StandartUtils.isInteger(args[0])) {
				amount = Integer.valueOf(args[0]);
			} else {
				sender.sendMessage(ChatColor.RED + "Please, specify the amount of items you want to generate (as first parameter)");
				return;
			}
			if(amount<1) {
				sender.sendMessage(ChatColor.RED + "The amount of items you want to generate should be more than zero!"); // TODO Better sending with colors or even [ERROR] stuff
				// I mean not a setting in the class, just a bunch of methods
				return;
			} else if(amount > BingoOperations.allowed.size()) {
				sender.sendMessage(ChatColor.RED + "The maximum amount of allowed (and different) items you can generate is " + BingoOperations.allowed.size() + "!");
				return;
			}
			References.amountOfitems = amount;
			References.materialList = BingoOperations.generateArray(amount);
			SavingData.saveTask(true);
			final int amSave = amount;
//			Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
//				@Override
//				public void run() {
//					GridOperations.generateImage(amSave);
//				}
//			});
			GridOperations.generateImage(amSave);
			SavingData.resetProgress();
			printList(sender, 1);
			for (Player p : Bukkit.getOnlinePlayers()) {
				if(!(sender instanceof Player && p == (Player) sender)) {
					printList(p, 1);
				}
			}
			References.inGame = true;
		} else {
			sender.sendMessage(ChatColor.RED + "Usage: /bstart [amount of items] <confirm if needed>");
		} 
	}
	
	public static void printList(CommandSender sender, int state) {
		String s = "";
		switch (References.mode) {
		case LINEAR:
			if(state==1) {
				s += ChatColor.WHITE + "The list of required items:\n";
			} else if(state==2) {
				s += ChatColor.WHITE + "Items to collect:\n";
			}
			for (int i = 0; i < References.materialList.size(); i++) {
				String color = ChatColor.GOLD + "";
				if(References.playersProgress.containsKey(sender.getName()) && References.playersProgress.get(sender.getName()).contains(References.materialList.get(i))) {
					color = ChatColor.GREEN + "";
				}
				s += color + ChatColor.BOLD + formatStringCase(References.materialList.get(i).name());
				if(i+1 < References.materialList.size()) {
					s += ChatColor.RESET + "" + ChatColor.WHITE + ", ";
				}
			}
			break;
		default:
			break;
		}
		if(s.trim()!="") {
			sender.sendMessage(s);
		}
	}
	
	public static String formatStringCase(String s) {
		String result = "";
		boolean upperCase = false;
		s = s.replace("_", " ");
		for (int i = 0; i < s.length(); i++) {
			if(i==0 || upperCase) {
				result += (s.charAt(i) + "").toUpperCase();
				upperCase = false;
			} else {
				result += (s.charAt(i) + "").toLowerCase();
			}
			if(s.charAt(i)==' ') upperCase = true;
		}
		return result;
	}
	
	public static void lockGame(CommandSender sender) {
		if(!References.inGame) {
			References.inGame = true;
			sender.sendMessage(ChatColor.GREEN + "The game resetiing was successfully locked");
		} else {
			sender.sendMessage(ChatColor.RED + "The game resetiing is already locked");
		}
	}
}
