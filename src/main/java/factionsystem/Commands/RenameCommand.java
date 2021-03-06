package factionsystem.Commands;

import factionsystem.Main;
import factionsystem.Objects.ClaimedChunk;
import factionsystem.Objects.Faction;
import factionsystem.Objects.LockedBlock;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static factionsystem.Subsystems.UtilitySubsystem.*;

public class RenameCommand {

    Main main = null;

    public RenameCommand(Main plugin) {
        main = plugin;
    }

    public void renameFaction(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mf.rename") || player.hasPermission("mf.default")) {
                if (args.length > 1) {
                    String oldName = getPlayersFaction(player.getUniqueId(), main.factions).getName();
                    String newName = createStringFromFirstArgOnwards(args);

                    // existence check
                    for (Faction faction : main.factions) {
                        if (faction.getName().equalsIgnoreCase(newName)) {
                            player.sendMessage(ChatColor.RED + "That name is already taken!");
                            return;
                        }
                    }

                    if (isInFaction(player.getUniqueId(), main.factions)) {
                        Faction playersFaction = getPlayersFaction(player.getUniqueId(), main.factions);
                        if (playersFaction.isOwner(player.getUniqueId())) {

                            // change name
                            playersFaction.setName(newName);
                            player.sendMessage(ChatColor.GREEN + "Faction name changed!");

                            // rename alliance, enemy, liege and vassal records
                            for (Faction faction : main.factions) {
                                if (faction.isAlly(oldName)) {
                                    faction.removeAlly(oldName);
                                    faction.addAlly(newName);
                                }
                                if (faction.isEnemy(oldName)) {
                                    faction.removeEnemy(oldName);
                                    faction.addEnemy(newName);
                                }
                                if (faction.isLiege(oldName)) {
                                    faction.setLiege(newName);
                                }
                                if (faction.isVassal(oldName)) {
                                    faction.removeVassal(oldName);
                                    faction.addVassal(newName);
                                }
                            }

                            // rename claimed chunk records
                            for (ClaimedChunk claimedChunk : main.claimedChunks) {
                                if (claimedChunk.getHolder().equalsIgnoreCase(oldName)) {
                                    claimedChunk.setHolder(newName);
                                }
                            }

                            // rename locked block records
                            for (LockedBlock lockedBlock : main.lockedBlocks) {
                                if (lockedBlock.getFactionName().equalsIgnoreCase(oldName)) {
                                    lockedBlock.setFaction(newName);
                                }
                            }

                            // Save again to overwrite current data
                            main.storage.save();

                        }
                        else {
                            player.sendMessage(ChatColor.RED + "You are not the owner of this faction!");
                        }
                    }
                }
                else {
                    player.sendMessage(ChatColor.RED + "Usage: /mf rename (newName)");
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "Sorry! You need to have the permission 'mf.rename' to use this command.");
            }
        }
    }
}
