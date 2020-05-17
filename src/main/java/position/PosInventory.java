package position;

import core.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class PosInventory {

    public static Inventory INV = Bukkit.createInventory(null, 27, ChatColor.DARK_BLUE + "Positions");

    public static Inventory addItem(ItemStack item, int slot) {

        INV.setItem(slot, item);

        return INV;
    }

    public static void pinPosition(Location location, String name, Player player, World.Environment environment) {

        String colorCode = "";

        switch (environment) {
            case NETHER:
                colorCode = "&5";
                break;
            case NORMAL:
                colorCode = "&a";
                break;
            case THE_END:
                colorCode = "&e";
                break;
        }


        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective(name, "dummy", Utils.colorize(colorCode + name));
        Score scoreX = objective.getScore("X: ");
        Score scoreY = objective.getScore("Y: ");
        Score scoreZ = objective.getScore("Z:  ");
        scoreX.setScore(location.getBlockX());
        scoreY.setScore(location.getBlockY());
        scoreZ.setScore(location.getBlockZ());
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(scoreboard);
    }


}
