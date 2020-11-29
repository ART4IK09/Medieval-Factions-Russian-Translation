package factionsystem.Commands;

import factionsystem.Main;
import factionsystem.Objects.Faction;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GrantIndependenceCommand {

    Main main = null;

    public GrantIndependenceCommand(Main plugin) {
        main = plugin;
    }

    public void grantIndependence(CommandSender sender, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("mf.grantindependence") || player.hasPermission("mf.default")) {

                if (args.length > 1) {

                    String targetFactionName = main.utilities.createStringFromFirstArgOnwards(args);

                    Faction playersFaction = main.utilities.getPlayersFaction(player.getUniqueId(), main.factions);
                    Faction targetFaction = main.utilities.getFaction(targetFactionName, main.factions);

                    if (targetFaction != null) {

                        if (playersFaction != null) {

                            if (playersFaction.isOwner(player.getUniqueId())) {
                                // if target faction is a vassal
                                if (targetFaction.isLiege(playersFaction.getName())) {
                                    targetFaction.setLiege("none");
                                    playersFaction.removeVassal(targetFaction.getName());

                                    // inform all players in that faction that they are now independent
                                    main.utilities.sendAllPlayersInFactionMessage(targetFaction, ChatColor.GREEN + "" + targetFactionName + " предоставил вашей фракции независимость!");

                                    // inform all players in players faction that a vassal was granted independence
                                    main.utilities.sendAllPlayersInFactionMessage(playersFaction, ChatColor.GREEN + "" + targetFactionName + " больше не вассальная фракция!");
                                }
                                else {
                                    player.sendMessage(ChatColor.RED + "Эта фракция не является вашим вассалом!");
                                }

                            }
                            else {
                                player.sendMessage(ChatColor.RED + "Чтобы использовать эту команду, вы должны быть владельцем своей фракции!");
                            }
                        }
                        else {
                            player.sendMessage(ChatColor.RED + "Вы должны состоять в фракции, чтобы использовать эту команду!");
                        }
                    }
                    else {
                        // faction doesn't exist, send message
                        player.sendMessage(ChatColor.RED + "Сожалею! Эта фракция не существует!");
                    }

                }
                else {
                    player.sendMessage(ChatColor.RED + "Использование: /mf grantindependence (имя фракции)");
                }

            }
            else {
                // send perm message
                player.sendMessage(ChatColor.RED + "Sorry! In order to use this command, you need the following permission: 'mf.grantindependence'");
            }
        }

    }

}
