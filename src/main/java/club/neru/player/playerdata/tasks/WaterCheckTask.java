package club.neru.player.playerdata.tasks;

import club.neru.annotations.AutoStartTimerTask;
import club.neru.player.playerdata.PlayerDataHandler;
import club.neru.player.playerdata.objects.nonpersistent.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

@AutoStartTimerTask
public class WaterCheckTask extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            UUID uuid = player.getUniqueId();

            Location location = player.getLocation();
            Block block = location.getBlock();
            Material blockMaterial = block.getType();

            PlayerData playerData = PlayerDataHandler.get(uuid);

            playerData.setInWater(blockMaterial == Material.WATER);
        }
    }
}
