package factionsystem.Commands;

import factionsystem.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BypassCommand {

    Main main = null;

    public BypassCommand(Main plugin) {
        main = plugin;
    }

    public void toggleBypass(CommandSender sender) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("mf.bypass") || player.hasPermission("mf.admin")) {

                if (!main.adminsBypassingProtections.contains(player.getUniqueId())) {
                    main.adminsBypassingProtections.add(player.getUniqueId());
                    player.sendMessage(ChatColor.GREEN + "Теперь вы обходите защиту, обеспечиваемую средневековыми фракциями.");
                }
                else {
                    main.adminsBypassingProtections.remove(player.getUniqueId());
                    player.sendMessage(ChatColor.GREEN + "Вы больше не обходите защиту, обеспечиваемую средневековыми фракциями.");
                }

            }
            else {
                player.sendMessage(ChatColor.RED + "Sorry! In order to use this command, you need the following permission: 'mf.bypass");
            }
        }

    }
}
