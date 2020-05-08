package position;

import java.util.HashMap;

import core.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class PosCommandExecutor implements CommandExecutor {

    private PosMain posmain;

    public PosCommandExecutor(PosMain posmain) {
        this.posmain = posmain;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String name = null;
        if (args.length == 1) {
            name = StringUtils.capitalize(args[0]);
        }

        if (command.getName().equalsIgnoreCase("position")) {
            if (args.length == 0) {
                Player p = null;
                if (sender instanceof Player) {
                    p = (Player) sender;
                }
                p.openInventory(PosInventory.INV);
                return true;
            } else if (args[0].equalsIgnoreCase("clear")) {

                posmain.deleteSaveFile((Player) sender);
                PosInventory.INV.clear();
                PosMain.locationMap.clear();
                PosMain.loctoInt.clear();
                PosMain.nametoInt.clear();
                sender.sendMessage(Utils.getPrefix("Position") + Utils.colorize("Alle Positions wurden entfernt!"));
                return true;

            } else if (PosMain.locationMap.containsKey(name)) {
                HashMap<String, Location> tempHash = PosMain.locationMap;
                Location temp = tempHash.get(name);

                String Dimension = PosMain.Dimensions.get(name).toString();


                switch (Dimension) {
                    case "NETHER":
                        sender.sendMessage(Utils.getPrefix("Position") + Utils.colorize("Die Position &5" + name + "&f liegt bei (&5" + temp.getBlockX() + "&f | &5" + temp.getBlockY() + "&f | &5" + temp.getBlockZ() + "&f)"));
                        break;
                    case "THE_END":
                        sender.sendMessage(Utils.getPrefix("Position") + Utils.colorize("Die Position &e" + name + "&f liegt bei (&e" + temp.getBlockX() + "&f | &e" + temp.getBlockY() + "&f | &e" + temp.getBlockZ() + "&f)"));
                        break;
                    case "NORMAL":
                        sender.sendMessage(Utils.getPrefix("Position") + Utils.colorize("Die Position &a" + name + "&f liegt bei (&a" + temp.getBlockX() + "&f | &a" + temp.getBlockY() + "&f | &a" + temp.getBlockZ() + "&f)"));
                }

                return true;

            } else {

                Player p = null;
                if (sender instanceof Player) {
                    p = (Player) sender;
                }


                Location temp = p.getLocation();
                PosMain.locationMap.put(name, temp);

                if (p.getWorld().getEnvironment().equals(Environment.NETHER)) {

                    PosMain.locationMap.put(name, temp);
                    PosMain.Dimensions.put(name, p.getWorld().getEnvironment());
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage(Utils.getPrefix("Position") + Utils.colorize("Die Position &5" + name + "&f wurde von &b" + sender.getName() + "&f erstellt!"));
                        player.sendMessage(Utils.getPrefix("Position") + Utils.colorize("Sie liegt bei (&5" + temp.getBlockX() + "&f | &5" + temp.getBlockY() + "&f | &5" + temp.getBlockZ() + "&f)"));
                    }
                    PosMain.addToInv(temp.getBlockX(), temp.getBlockY(), temp.getBlockZ(), name, p.getWorld().getEnvironment().toString());
                    posmain.saveToFile(temp, name, p.getWorld().getEnvironment());
                } else if (p.getWorld().getEnvironment().equals(Environment.THE_END)) {
                    PosMain.locationMap.put(name, temp);
                    PosMain.Dimensions.put(name, p.getWorld().getEnvironment());
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage(Utils.getPrefix("Position") + Utils.colorize("Die Position &e" + name + "&f wurde von &b" + sender.getName() + "&f erstellt!"));
                        player.sendMessage(Utils.getPrefix("Position") + Utils.colorize("Sie liegt bei (&e" + temp.getBlockX() + "&f | &e" + temp.getBlockY() + "&f | &e" + temp.getBlockZ() + "&f)"));
                    }
                    PosMain.addToInv(temp.getBlockX(), temp.getBlockY(), temp.getBlockZ(), name, p.getWorld().getEnvironment().toString());
                    posmain.saveToFile(temp, name, p.getWorld().getEnvironment());
                } else {
                    PosMain.locationMap.put(name, temp);
                    PosMain.Dimensions.put(name, p.getWorld().getEnvironment());
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage(Utils.getPrefix("Position") + Utils.colorize("Die Position &a" + name + "&f wurde von &b" + sender.getName() + "&f erstellt!"));
                        player.sendMessage(Utils.getPrefix("Position") + Utils.colorize("Sie liegt bei (&a" + temp.getBlockX() + "&f | &a" + temp.getBlockY() + "&f | &a" + temp.getBlockZ() + "&f)"));
                    }
                    PosMain.addToInv(temp.getBlockX(), temp.getBlockY(), temp.getBlockZ(), name, p.getWorld().getEnvironment().toString());
                    posmain.saveToFile(temp, name, p.getWorld().getEnvironment());
                }
            }
        }
        return false;
    }

}
