package com.gmail.mrphpfan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ShopStocks extends JavaPlugin implements Listener {
	
	private TransactionListener transListener;
	
	@Override
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		
		createItemList();
		
		transListener = new TransactionListener(this);
		getServer().getPluginManager().registerEvents(transListener, this);
		
		getLogger().info("ShopStocks Enabled.");
	}
	
	@Override
	public void onDisable(){
		getLogger().info("ShopStocks Disabled.");
	}
	
	private void createItemList(){
		
		File itemListFile = new File("plugins/ShopStocks/items.txt");
		//create the file if it doesn't exist already
		try{
			if(!itemListFile.exists()){
				itemListFile.createNewFile();
				
				//write contents to it
				try{
					PrintWriter out = new PrintWriter(itemListFile);
					for(Material mat : Material.values()){
						//format: Material:Weight
						//this will be tough because we have to sense when something isn't getting bought, or when an event isn't happening.
						String ln = "";
						out.println(ln);
					}
					out.close();
				}catch(FileNotFoundException e) {
					e.printStackTrace();
				}
				
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
}
