package com.gmail.mrphpfan;

import net.ess3.api.events.SignInteractEvent;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.Acrobot.ChestShop.Events.TransactionEvent;
import com.Acrobot.ChestShop.Events.TransactionEvent.TransactionType;
import com.earth2me.essentials.IUser;
import com.earth2me.essentials.signs.EssentialsSign.ISign;
import com.earth2me.essentials.signs.SignSell;

public class TransactionListener implements Listener {
	
	private ShopStocks plugin;
	
	public TransactionListener(ShopStocks plugin){
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	/**
	 * This is used for detecting sell-to AdminShops
	 * @param e
	 */
	@EventHandler
	private void onPlayerTransaction(TransactionEvent e){
		String shopOwnerName = e.getOwner().getName();
		Player client = e.getClient();
		//we only care if it is AdminShop
		if(shopOwnerName.equals("AdminShop")){
			//get the item type
			ItemStack[] items = e.getStock();
			if(items.length == 0){
				//strange
				return;
			}
			Material itemType = items[0].getType();
			
			client.sendMessage(itemType.toString());
			//check if it is buy or sell
			TransactionType transactionType = e.getTransactionType();
			if(transactionType == TransactionType.BUY){
				//increase the weight of any stock groups that this item is in
			}else if(transactionType == TransactionType.SELL){
				
			}
		}
		
	}
	
	/**
	 * Detect when user interacts with essentials signs
	 * @param e
	 */
	@EventHandler
	private void onEssentialSignInteract(SignInteractEvent e){
		IUser user = e.getUser();
		//check if this is a sell sign
		if(e.getEssentialsSign().getClass().equals(SignSell.class)){
			ISign sign = e.getSign();
			//get the quantity, material, and price
			int quantity = Integer.parseInt(sign.getLine(1));
			Material itemMaterial = Material.valueOf(sign.getLine(2));
			//strip the dollar sign off the front of the last line
			double price = Double.parseDouble(sign.getLine(3).substring(1));
			
			double total = quantity * price;
			
		}
	}
	
	/**
	 * Returns true if given string is an integer, false if not
	 */
	private boolean isInteger(String str){
		try{
			Integer.parseInt(str);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}
	
}
