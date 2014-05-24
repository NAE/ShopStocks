package com.gmail.mrphpfan;

import org.bukkit.event.Listener;

public class CommandListener implements Listener {
	
	private ShopStocks plugin;
	
	public CommandListener(ShopStocks plugin){
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	
}
