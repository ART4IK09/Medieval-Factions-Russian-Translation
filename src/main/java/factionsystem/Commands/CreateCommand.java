package factionsystem.Commands;

import factionsystem.Main;
import factionsystem.Objects.Faction;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static factionsystem.Subsystems.UtilitySubsystem.createStringFromFirstArgOnwards;
import static factionsystem.Subsystems.UtilitySubsystem.getPlayersPowerRecord;

public class CreateCommand {

    Main main = null;

    public CreateCommand(Main plugin) {
        main = plugin;
    }

    public boolean createFaction(CommandSender sender, String[] args) {
        // player check
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // player membership check
            for (Faction faction : main.factions) {
                if (faction.isMember(player.getUniqueId())) {
                    player.sendMessage(ChatColor.RED + "Извините, вы уже во фракции. Оставьте, если хотите создать другой.");
                    return false;
                }
            }

            // argument check
            if (args.length > 1) {

                // creating name from arguments 1 to the last one
                String name = createStringFromFirstArgOnwards(args);

                // faction existence check
                boolean factionExists = false;
                for (Faction faction : main.factions) {
                    if (faction.getName().equalsIgnoreCase(name)) {
                        factionExists = true;
                        break;
                    }
                }

                if (!factionExists) {

                    // actual faction creation
                    Faction temp = new Faction(name, player.getUniqueId(), main.getConfig().getInt("initialMaxPowerLevel"), main);
                    main.factions.add(temp);
                    // TODO: Make thread safe
                    main.factions.get(main.factions.size() - 1).addMember(player.getUniqueId(), getPlayersPowerRecord(player.getUniqueId(), main.playerPowerRecords).getPowerLevel());
                    System.out.println("Faction " + name + " created.");
                    player.sendMessage(ChatColor.AQUA + "Faction " + name + " created.");
                    return true;
                }
                else {
                    player.sendMessage(ChatColor.RED + "Сожалею! Эта фракция уже существует.");
                    return false;
                }
            } else {

                // wrong usage
                sender.sendMessage(ChatColor.RED + "Использование: /mf create [имя фракции]");
                return false;
            }
        }
        return false;
    }
}
