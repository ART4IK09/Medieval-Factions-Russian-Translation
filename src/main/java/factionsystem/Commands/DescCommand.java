package factionsystem.Commands;

import factionsystem.Main;
import factionsystem.Objects.Faction;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DescCommand {

    Main main = null;

    public DescCommand(Main plugin) {
        main = plugin;
    }

    public boolean setDescription(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            boolean owner = false;
            for (Faction faction : main.factions) {
                if (faction.isOwner(player.getUniqueId())) {
                    owner = true;
                    if (args.length > 1) {

                        // set arg[1] - args[args.length-1] to be the description with spaces put in between
                        String newDesc = "";
                        for (int i = 1; i < args.length; i++) {
                            newDesc = newDesc + args[i];
                            if (!(i == args.length - 1)) {
                                newDesc = newDesc + " ";
                            }
                        }

                        faction.setDescription(newDesc);
                        player.sendMessage(ChatColor.AQUA + "Описание установлено!");
                        return true;
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Использовние: /mf desc (описание)");
                        return false;
                    }
                }
            }
            if (!owner) {
                player.sendMessage(ChatColor.RED + "Чтобы использовать эту команду, вы должны быть владельцем фракции.");
                return false;
            }
        }
        return false;
    }

}
