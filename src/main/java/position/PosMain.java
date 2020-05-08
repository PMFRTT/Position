package position;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

import core.CoreMain;
import core.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class PosMain extends JavaPlugin implements Listener {

    private PosCommandExecutor commandExecutor;
    public static HashMap<String, Location> locationMap = new HashMap<String, Location>();
    public static HashMap<Integer, Location> loctoInt = new HashMap<Integer, Location>();
    public static HashMap<Integer, String> nametoInt = new HashMap<Integer, String>();
    public static HashMap<String, Environment> Dimensions = new HashMap<String, Environment>();
    public static Collection<? extends Player> players = Bukkit.getOnlinePlayers();
    private static int i = 0;
    private static ItemStack posItemStack = null;
    private static ItemMeta posItemMeta;
    private static ArrayList<String> lore = new ArrayList<String>();

    public void onEnable() {

        CoreMain.setPlugin(this);

        commandExecutor = new PosCommandExecutor(this);
        getCommand("position").setExecutor(commandExecutor);

        Bukkit.getPluginManager().registerEvents(this, this);


        try {

            File dataFolder = getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdir();
            }

            File positions = new File(getDataFolder(), "locations.txt");
            if (!positions.exists()) {

                positions.createNewFile();

            }

            Scanner scanner = new Scanner(new FileReader(positions));

            while (scanner.hasNext()) {

                String line = scanner.nextLine();
                int endX = line.indexOf(" ");
                int endY = line.indexOf(" ", endX + 2);
                int endZ = line.indexOf(" ", endY + 2);
                int endName = line.indexOf(" ", endZ + 2);


                String X = line.substring(2, endX);
                String Y = line.substring(endX + 3, endY);
                String Z = line.substring(endY + 3, endZ);
                String Name = line.substring(endZ + 6, endName);
                String Dimension = line.substring(endName + 5);

                Location temp = new Location(Bukkit.getWorld("world"), Integer.parseInt(X), Integer.parseInt(Y),
                        Integer.parseInt(Z));
                locationMap.put(Name, temp);
                loctoInt.put(i, temp);
                nametoInt.put(i, Name);


                switch (Dimension) {
                    case "NETHER":
                        Dimensions.put(Name, Environment.NETHER);
                        posItemStack = new ItemStack(Material.NETHERRACK, 1);
                        posItemMeta = posItemStack.getItemMeta();
                        posItemMeta.setDisplayName(ChatColor.DARK_PURPLE + Name);
                        lore.clear();
                        lore.add(ChatColor.GRAY + "X: " + X);
                        lore.add(ChatColor.GRAY + "Y: " + Y);
                        lore.add(ChatColor.GRAY + "Z: " + Z);
                        posItemMeta.setLore(lore);
                        posItemStack.setItemMeta(posItemMeta);
                        PosInventory.addItem(posItemStack, i);
                        break;
                    case "THE_END":
                        Dimensions.put(Name, Environment.THE_END);
                        posItemStack = new ItemStack(Material.END_STONE, 1);
                        posItemMeta = posItemStack.getItemMeta();
                        posItemMeta.setDisplayName(ChatColor.YELLOW + Name);
                        lore.clear();
                        lore.add(ChatColor.GRAY + "X: " + X);
                        lore.add(ChatColor.GRAY + "Y: " + Y);
                        lore.add(ChatColor.GRAY + "Z: " + Z);
                        posItemMeta.setLore(lore);
                        posItemStack.setItemMeta(posItemMeta);
                        PosInventory.addItem(posItemStack, i);
                        break;
                    case "NORMAL":
                        Dimensions.put(Name, Environment.NORMAL);
                        posItemStack = new ItemStack(Material.GRASS_BLOCK, 1);
                        posItemMeta = posItemStack.getItemMeta();
                        posItemMeta.setDisplayName(ChatColor.GREEN + Name);
                        lore.clear();
                        lore.add(ChatColor.GRAY + "X: " + X);
                        lore.add(ChatColor.GRAY + "Y: " + Y);
                        lore.add(ChatColor.GRAY + "Z: " + Z);
                        posItemMeta.setLore(lore);
                        posItemStack.setItemMeta(posItemMeta);
                        PosInventory.addItem(posItemStack, i);
                        break;
                }
                i++;

            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onDisable() {

    }

    public void deleteFile(boolean isAdmin) {
        if (isAdmin) {
            File positions = new File(getDataFolder(), "locations.txt");
            if (positions.exists()) {
                positions.delete();
            }
        }
    }

    public static void addToInv(int X, int Y, int Z, String name, String dim) {
        switch (dim) {
            case "NETHER":
                Dimensions.put(name, Environment.NETHER);
                posItemStack = new ItemStack(Material.NETHERRACK, 1);
                posItemMeta = posItemStack.getItemMeta();
                posItemMeta.setDisplayName(ChatColor.DARK_PURPLE + StringUtils.capitalize(name));
                lore.clear();
                lore.add(ChatColor.GRAY + "X: " + X);
                lore.add(ChatColor.GRAY + "Y: " + Y);
                lore.add(ChatColor.GRAY + "Z: " + Z);
                posItemMeta.setLore(lore);
                posItemStack.setItemMeta(posItemMeta);
                PosInventory.addItem(posItemStack, i);
                loctoInt.put(i, new Location(Bukkit.getWorld("world"), X, Y, Z));
                nametoInt.put(i, name);
                break;
            case "THE_END":
                Dimensions.put(name, Environment.THE_END);
                posItemStack = new ItemStack(Material.END_STONE, 1);
                posItemMeta = posItemStack.getItemMeta();
                posItemMeta.setDisplayName(ChatColor.YELLOW + StringUtils.capitalize(name));
                lore.clear();
                lore.add(ChatColor.GRAY + "X: " + X);
                lore.add(ChatColor.GRAY + "Y: " + Y);
                lore.add(ChatColor.GRAY + "Z: " + Z);
                posItemMeta.setLore(lore);
                posItemStack.setItemMeta(posItemMeta);
                PosInventory.addItem(posItemStack, i);
                loctoInt.put(i, new Location(Bukkit.getWorld("world"), X, Y, Z));
                nametoInt.put(i, name);

                break;
            case "NORMAL":
                Dimensions.put(name, Environment.NORMAL);
                posItemStack = new ItemStack(Material.GRASS_BLOCK, 1);
                posItemMeta = posItemStack.getItemMeta();
                posItemMeta.setDisplayName(ChatColor.GREEN + StringUtils.capitalize(name));
                lore.clear();
                lore.add(ChatColor.GRAY + "X: " + X);
                lore.add(ChatColor.GRAY + "Y: " + Y);
                lore.add(ChatColor.GRAY + "Z: " + Z);
                posItemMeta.setLore(lore);
                posItemStack.setItemMeta(posItemMeta);
                PosInventory.addItem(posItemStack, i);
                loctoInt.put(i, new Location(Bukkit.getWorld("world"), X, Y, Z));
                nametoInt.put(i, name);

                break;
        }
        i++;
    }

    public void saveToFile(Location l, String name, Environment e) {

        try {

            File dataFolder = getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdir();
            }

            File positions = new File(getDataFolder(), "locations.txt");
            if (!positions.exists()) {

                positions.createNewFile();

            }

            FileWriter fw = new FileWriter(positions, true);
            @SuppressWarnings("resource")
            PrintWriter pw = new PrintWriter(fw);

            pw.println("X:" + (int) l.getX() + " Y:" + (int) l.getY() + " Z:" + (int) l.getZ() + " name:" + name
                    + " dim:" + e.toString());
            pw.flush();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void deleteSaveFile(Player p) {


        File positions = new File(getDataFolder(), "locations.txt");
        if (!positions.exists()) {
            p.sendMessage(ChatColor.DARK_RED + "Die Liste ist leer!");
        } else {
            i = 0;
            positions.delete();
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (PosInventory.INV != null) {
            if (e.getWhoClicked() instanceof Player) {
                if (e.getClickedInventory() == PosInventory.INV) {
                    int j = e.getSlot();
                    e.setCancelled(true);
                    if (e.getCurrentItem() != null) {
                        e.getWhoClicked().sendMessage(Utils.getPrefix("Position") + Utils.colorize("Name: &6" + StringUtils.capitalize(nametoInt.get(j))));
                        e.getWhoClicked().sendMessage(Utils.getPrefix("Position") + Utils.colorize("(&6" + loctoInt.get(j).getBlockX() + "&f | &6" + loctoInt.get(j).getBlockY() + "&f | &6" + loctoInt.get(j).getBlockZ() + "&f)"));
                    }
                }
            }
        }
    }

}