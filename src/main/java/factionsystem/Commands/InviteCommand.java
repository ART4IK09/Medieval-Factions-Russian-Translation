package factionsystem.Commands;

import factionsystem.Main;
import factionsystem.Objects.Faction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static factionsystem.Subsystems.UtilitySubsystem.findUUIDBasedOnPlayerName;
import static factionsystem.Subsystems.UtilitySubsystem.isInFaction;
import static org.bukkit.Bukkit.getServer;

public class InviteCommand {

    Main main = null;

    public InviteCommand(Main plugin) {
        main = plugin;
    }

    public boolean invitePlayer(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (isInFaction(player.getUniqueId(), main.factions)) {
                for (Faction faction : main.factions) {
                    if (faction.isOwner(player.getUniqueId()) || faction.isOfficer(player.getUniqueId())) {
                        if (args.length > 1) {
                            UUID playerUUID = findUUIDBasedOnPlayerName(args[1]);
                            // invite if player isn't in a faction already
                            if (!(isInFaction(playerUUID, main.factions))) {
                                faction.invite(playerUUID);
                                try {
                                    Player target = Bukkit.getServer().getPlayer(args[1]);
                                    target.sendMessage(ChatColor.GREEN + "Вас пригласили в " + faction.getName() + "! Напишите /mf join " + faction.getName() + " чтобы присоединится.");
                                } catch (Exception ignored) {

                                }
                                player.sendMessage(ChatColor.GREEN + "Приглашение отправлено!");

                                int seconds = 60 * 60 * 24;

                                // make invitation expire in 24 hours, if server restarts it also expires since invites aren't saved
                                getServer().getScheduler().runTaskLater(main, new Runnable() {
                                    @Override
                                    public void run() {
                                        faction.uninvite(playerUUID);
                                        try {
                                            Player target = Bukkit.getServer().getPlayer(args[1]);
                                            target.sendMessage(ChatColor.RED + "Ваше приглашение на " + faction.getName() + " истекло!.");
                                        } catch (Exception ignored) {
                                            // player offline
                                        }
                                    }
                                }, seconds * 20);

                                return true;
                            }
                            else {
                                player.sendMessage(ChatColor.RED + "Этот игрок уже состоит во фракции, извините!");
                                return false;
                            }


                        }
                        else {
                            player.sendMessage(ChatColor.RED + "Использование: /mf invite (имя игрока)");
                            return false;
                        }
                    }
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "Чтобы использовать эту команду, вы должны быть во фракции.");
            }
        }
        return false;
    }

}
