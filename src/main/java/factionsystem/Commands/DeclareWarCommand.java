package factionsystem.Commands;

import factionsystem.Main;
import factionsystem.Objects.Faction;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static factionsystem.Subsystems.UtilitySubsystem.*;

public class DeclareWarCommand {

    Main main = null;

    public DeclareWarCommand(Main plugin) {
        main = plugin;
    }

    public void declareWar(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            boolean owner = false;
            for (Faction faction : main.factions) {
                // if player is the owner or officer
                if (faction.isOwner(player.getUniqueId()) || faction.isOfficer(player.getUniqueId())) {
                    owner = true;
                    // if there's more than one argument
                    if (args.length > 1) {

                        // get name of faction
                        String factionName = createStringFromFirstArgOnwards(args);

                        // check if faction exists
                        for (int i = 0; i < main.factions.size(); i++) {
                            if (main.factions.get(i).getName().equalsIgnoreCase(factionName)) {

                                if (!(faction.getName().equalsIgnoreCase(factionName))) {

                                    // check that enemy is not already on list
                                    if (!(faction.isEnemy(factionName))) {

                                        // if trying to declare war on a vassal
                                        if (main.factions.get(i).hasLiege()) {

                                            // if faction is vassal of declarer
                                            if (faction.isVassal(factionName)) {
                                                player.sendMessage(ChatColor.RED + "Вы не можете объявить войну своему вассалу!");
                                                return;
                                            }

                                            // if lieges aren't the same
                                            if (!main.factions.get(i).getLiege().equalsIgnoreCase(faction.getLiege())) {
                                                player.sendMessage(ChatColor.RED + "Вы не можете объявить войну этой фракции, поскольку они вассалы! Вы должны объявить войну своему сюзерену " + main.factions.get(i).getLiege() + " instead!");
                                                return;
                                            }

                                        }

                                        // disallow if trying to declare war on liege
                                        if (faction.isLiege(factionName)) {
                                            player.sendMessage(ChatColor.RED + "Вы не можете объявить войну своему сюзерену! Попробуйте вместо этого '/ mf declareindependence'!");
                                            return;
                                        }

                                        // check to make sure we're not allied with this faction
                                        if (!faction.isAlly(factionName)) {

                                            // add enemy to declarer's faction's enemyList and the enemyLists of its allies
                                            faction.addEnemy(factionName);

                                            // add declarer's faction to new enemy's enemyList
                                            main.factions.get(i).addEnemy(faction.getName());

                                            for (int j = 0; j < main.factions.size(); j++) {
                                                if (main.factions.get(j).getName().equalsIgnoreCase(factionName)) {
                                                    main.utilities.sendAllPlayersOnServerMessage(ChatColor.RED + faction.getName() + " объявил войну " + factionName + "!");
                                                }
                                            }

                                            // invoke alliances
                                            invokeAlliances(main.factions.get(i).getName(), faction.getName(), main.factions);
                                        }
                                        else {
                                            player.sendMessage(ChatColor.RED + "Вы не можете объявить войну своему союзнику!");
                                        }

                                    }
                                    else {
                                        player.sendMessage(ChatColor.RED + "Ваша фракция уже воюет с " + factionName);
                                    }

                                }
                                else {
                                    player.sendMessage(ChatColor.RED + "Вы не можете объявить войну своей фракции.");
                                }
                            }
                        }

                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Использование: /mf declarewar (имя фракции)");
                    }
                }
            }
            if (!owner) {
                player.sendMessage(ChatColor.RED + "Вы должны быть владельцем фракции или быть ее офицером, чтобы использовать эту команду..");
            }
        }
    }
}
