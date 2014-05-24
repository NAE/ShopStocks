package com.gmail.mrphpfan;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.Acrobot.ChestShop.Events.TransactionEvent;
import com.Acrobot.ChestShop.Events.TransactionEvent.TransactionType;

public class TransactionListener implements Listener {
	
	private ShopStocks plugin;
	
	public TransactionListener(ShopStocks plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerTransaction(TransactionEvent e){
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
	
	
}
