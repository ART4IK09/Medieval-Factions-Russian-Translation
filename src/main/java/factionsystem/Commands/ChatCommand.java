package factionsystem.Commands;

import factionsystem.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static factionsystem.Subsystems.UtilitySubsystem.isInFaction;

public class ChatCommand {

    Main main = null;

    public ChatCommand(Main plugin) {
        main = plugin;
    }

    public void toggleFactionChat(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("mf.chat") || player.hasPermission("mf.default")) {
                if (isInFaction(player.getUniqueId(), main.factions)) {
                    if (!main.playersInFactionChat.contains(player.getUniqueId())) {
                        main.playersInFactionChat.add(player.getUniqueId());
                        player.sendMessage(ChatColor.GREEN + "Ты теперь говоришь в чат фракции!");
                    }
                    else {
                        main.playersInFactionChat.remove(player.getUniqueId());
                        player.sendMessage(ChatColor.GREEN + "Ты больше не в чате фракции!");
                    }
                }
                else {
                    player.sendMessage(ChatColor.RED + "Ты должен состоять в фракции чтобы использовать это!");
                }

            }
            else {
                player.sendMessage(ChatColor.RED + "Sorry! In order to use this command, you need the following permission: 'mf.chat'");
            }
        }
    }

}
