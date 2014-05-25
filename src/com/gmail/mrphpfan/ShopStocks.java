package com.gmail.mrphpfan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ShopStocks extends JavaPlugin implements Listener {
	
	public static final String ITEM_WEIGHTS_FILE_PATH = "plugins/ShopStocks/itemWeights.txt";
	
	public HashMap<Material, Float> itemWeights = new HashMap<Material, Float>();
	
	private TransactionListener transListener;
	private CommandListener commandListener;
	
	@Override
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		
		loadConfiguration();
		
		transListener = new TransactionListener(this);
		commandListener = new CommandListener(this);
		
		getLogger().info("ShopStocks Enabled.");
	}
	
	@Override
	public void onDisable(){
		//update item weights with ones we have stored in memory
		writeItemList();
		getLogger().info("ShopStocks Disabled.");
	}
	
	/**
	 * Loads in config and generates necessary plugin files
	 * if they don't already exist
	 */
	private void loadConfiguration(){
		File pluginFolder = this.getDataFolder();
		if(!pluginFolder.exists()){
			pluginFolder.mkdir();
		}
		
		//create a list of items if it does not already exist
		createDefaultItemList();
		//read in the item list
		readItemList();
	}
	
	/**
	 * Creates a list of all items with weights defaulting at 1.0, if this file does not already exist
	 * Available range for weights is 0.0 -> 1.0
	 */
	private void createDefaultItemList(){
		
		File itemListFile = new File(ITEM_WEIGHTS_FILE_PATH);
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
						String ln = mat.toString() + ":1.0";
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
	
	/**
	 * Reads in item weights from file
	 */
	private void readItemList(){
		File itemListFile = new File(ITEM_WEIGHTS_FILE_PATH);
		try {
			Scanner scanner1 = new Scanner(itemListFile);
			while(scanner1.hasNextLine()){
				String itemLine = scanner1.nextLine();
				//get the item name and its current weight
				int indexOfColon = itemLine.indexOf(":");
				String itemType = itemLine.substring(0,indexOfColon);
				float itemWeight = Float.parseFloat(itemLine.substring(indexOfColon+1));
				Material materialType = Material.valueOf(itemType);
				itemWeights.put(materialType, itemWeight);
			}
			scanner1.close();
		} catch (FileNotFoundException e) {
			getLogger().warning("Unable to read in item weights for some reason, disabling ShopStocks.");
			//disable plugin
			this.setEnabled(false);
		}
	}
	
	/**
	 * Writes new item weights back to file
	 */
	private void writeItemList(){
		File itemListFile = new File(ITEM_WEIGHTS_FILE_PATH);
		if(itemListFile.exists()){
			//write weights to it
			try{
				PrintWriter out = new PrintWriter(itemListFile);
				for(Map.Entry<Material, Float> entry : itemWeights.entrySet()){
					Material materialType = entry.getKey();
					float itemWeight = entry.getValue();
					String ln = materialType.toString() + ":" + itemWeight;
					out.println(ln);
				}
				out.close();
			}catch(FileNotFoundException e) {
				getLogger().warning("Unable to write to ShopStocks item weights file.");
				e.printStackTrace();
			}
		}
	}
}
