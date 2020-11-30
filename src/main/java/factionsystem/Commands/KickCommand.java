package factionsystem.Commands;

import factionsystem.Main;
import factionsystem.Objects.Faction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static factionsystem.Subsystems.UtilitySubsystem.*;

public class KickCommand {

    Main main = null;

    public KickCommand(Main plugin) {
        main = plugin;
    }

    public boolean kickPlayer(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 1) {
                boolean owner = false;
                for (Faction faction : main.factions) {
                    if (faction.isOwner(player.getUniqueId()) || faction.isOfficer(player.getUniqueId())) {
                        owner = true;
                        UUID playerUUID = findUUIDBasedOnPlayerName(args[1]);
                        if (faction.isMember(playerUUID)) {
                            if (!(args[1].equalsIgnoreCase(player.getName()))) {
                                if (!(playerUUID.equals(faction.getOwner()))) {

                                    if (faction.isOfficer(playerUUID)) {
                                        faction.removeOfficer(playerUUID);
                                    }

                                    main.playersInFactionChat.remove(playerUUID);

                                    faction.removeMember(playerUUID, getPlayersPowerRecord(player.getUniqueId(), main.playerPowerRecords).getPowerLevel());
                                    try {
                                        sendAllPlayersInFactionMessage(faction, ChatColor.RED + args[1] + " был выгнан из " + faction.getName());
                                    } catch (Exception ignored) {

                                    }
                                    try {
                                        Player target = Bukkit.getServer().getPlayer(args[1]);
                                        target.sendMessage(ChatColor.RED + "Вас выгнали из вашей фракции " + player.getName() + ".");
                                    } catch (Exception ignored) {

                                    }
                                    return true;
                                }
                                else {
                                    player.sendMessage(ChatColor.RED + "Вы не можете выгнать хозяина.");
                                    return false;
                                }

                            }
                            else {
                                player.sendMessage(ChatColor.RED + "Ты не можешь выгнать себя.");
                                return false;
                            }

                        }
                    }
                }

                if (!owner) {
                    player.sendMessage(ChatColor.RED + "Чтобы использовать эту команду, вы должны быть владельцем фракции.");
                    return false;
                }

            } else {
                player.sendMessage(ChatColor.RED + "Использование: /mf kick (имя игрока)");
                return false;
            }
        }
        return false;
    }

}
