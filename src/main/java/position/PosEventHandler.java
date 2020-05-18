package position;

import core.CoreSendStringPacket;
import core.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

import static position.PosMain.*;

public class PosEventHandler implements Listener {

    PosMain main;


    public PosEventHandler(PosMain main) {
        this.main = main;
    }

    public void initialize() {
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (PosInventory.INV != null) {
            if (e.getWhoClicked() instanceof Player) {
                if (e.getClickedInventory() == PosInventory.INV) {
                    int j = e.getSlot();
                    e.setCancelled(true);
                    if (e.getCurrentItem() != null) {
                        if (e.isLeftClick()) {
                            e.getWhoClicked().sendMessage(Utils.getPrefix("Position") + Utils.colorize("Name: &6" + StringUtils.capitalize(nametoInt.get(j))));
                            e.getWhoClicked().sendMessage(Utils.getPrefix("Position") + Utils.colorize("(&6" + loctoInt.get(j).getBlockX() + "&f | &6" + loctoInt.get(j).getBlockY() + "&f | &6" + loctoInt.get(j).getBlockZ() + "&f)"));
                        } else if (e.isRightClick()) {
                            Player player = (Player) e.getWhoClicked();
                            player.closeInventory();
                            CoreSendStringPacket.sendPacketToTitle(player, Utils.colorize("&7" + StringUtils.capitalize(nametoInt.get(j))), Utils.colorize("wurde &aangepinnt&f!"));
                            PosInventory.pinPosition(loctoInt.get(j), nametoInt.get(j), player, Dimensions.get(nametoInt.get(j)));
                        }
                    }
                }
            }
        }
    }


}
