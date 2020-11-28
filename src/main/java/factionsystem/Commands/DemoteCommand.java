package factionsystem.Commands;

import factionsystem.Main;
import factionsystem.Objects.Faction;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static factionsystem.Subsystems.UtilitySubsystem.*;
import static org.bukkit.Bukkit.getServer;

public class DemoteCommand {

    Main main = null;

    public DemoteCommand(Main plugin) {
        main = plugin;
    }

    public void demotePlayer(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (isInFaction(player.getUniqueId(), main.factions)) {
                if (args.length > 1) {
                    for (Faction faction : main.factions) {
                        UUID officerUUID = findUUIDBasedOnPlayerName(args[1]);
                        if (officerUUID != null && faction.isOfficer(officerUUID)) {
                            if (faction.isOwner(player.getUniqueId())) {
                                if (faction.removeOfficer(officerUUID)) {

                                    player.sendMessage(ChatColor.GREEN + "Игрок понижен в звании!");

                                    try {
                                        Player target = getServer().getPlayer(officerUUID);
                                        target.sendMessage(ChatColor.RED + "Вы были понижены до статуса члена вашей фракции.");
                                    }
                                    catch(Exception ignored) {

                                    }
                                }
                                else {
                                    player.sendMessage(ChatColor.RED + "Этот игрок не офицер вашей фракции!");
                                }
                                return;
                            }
                        }
                    }
                }
                else {
                    player.sendMessage(ChatColor.RED + "Использование: /mf demote (имя игрока)");
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "Чтобы использовать эту команду, вы должны быть во фракции.");
            }
        }
    }
}
