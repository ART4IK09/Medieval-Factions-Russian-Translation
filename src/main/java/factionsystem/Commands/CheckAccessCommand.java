package factionsystem.Commands;

import factionsystem.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckAccessCommand {

    Main main = null;

    public CheckAccessCommand(Main plugin) {
        main = plugin;
    }

    public void checkAccess(CommandSender sender, String[] args) {
        // if sender is player and if player has permission
        if (sender instanceof Player && (((Player) sender).hasPermission("mf.checkaccess") || ((Player) sender).hasPermission("mf.default"))) {

            Player player = (Player) sender;

            if (args.length > 1) {
                if (args[1].equalsIgnoreCase("cancel")) {
                    player.sendMessage(ChatColor.RED + "Cancelled!");
                    if (main.playersCheckingAccess.contains(player.getUniqueId())) {
                        main.playersCheckingAccess.remove(player.getUniqueId());
                        return;
                    }
                }
            }

            if (!main.playersCheckingAccess.contains(player.getUniqueId())) {
                main.playersCheckingAccess.add(player.getUniqueId());
                player.sendMessage(ChatColor.GREEN + "Щелкните правой кнопкой мыши заблокированный блок, чтобы проверить, кто имеет к нему доступ! Введите '/ mf checkaccess cancel', чтобы отменить");
            }
            else {
                player.sendMessage(ChatColor.RED + "Вы уже ввели эту команду! Введите '/ mf checkaccess cancel' для отмены!");
            }

        }
    }

}
