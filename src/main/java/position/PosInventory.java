package position;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class PosInventory {
	
	public static Inventory INV = Bukkit.createInventory(null, 27, ChatColor.DARK_BLUE + "Positions");

	public static Inventory addItem(ItemStack item, int slot) {

		INV.setItem(slot, item);
		
		return INV;
	}
	

}
