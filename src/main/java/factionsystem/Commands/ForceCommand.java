package factionsystem.Commands;

import factionsystem.Main;
import factionsystem.Objects.Faction;
import factionsystem.Objects.PlayerPowerRecord;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

import static factionsystem.Subsystems.UtilitySubsystem.*;

public class ForceCommand {

    Main main = null;

    public ForceCommand(Main plugin) {
        main = plugin;
    }

    public boolean force(CommandSender sender, String[] args) {
        if (args.length > 1) {
            if (args[1].equalsIgnoreCase("save")) {
                return forceSave(sender);
            }

            if (args[1].equalsIgnoreCase("load")) {
                return forceLoad(sender);
            }

            if (args[1].equalsIgnoreCase("peace")) {
                return forcePeace(sender, args);
            }
            
            if (args[1].equalsIgnoreCase("demote")) {
                return forceDemote(sender, args);
            }

            if (args[1].equalsIgnoreCase("join")) {
                return forceJoin(sender, args);
            }

            if (args[1].equalsIgnoreCase("kick")) {
                return forceKick(sender, args);
            }
            if (args[1].equalsIgnoreCase("power")) {
                return forcePower(sender, args);
            }
        }
        // show usages
        sender.sendMessage(ChatColor.RED + "Подкоманды:");
        sender.sendMessage(ChatColor.RED + "/mf force save");
        sender.sendMessage(ChatColor.RED + "/mf force load");
        sender.sendMessage(ChatColor.RED + "/mf force peace 'фракция1' 'фракция'");
        sender.sendMessage(ChatColor.RED + "/mf force demote (игрок)");
        sender.sendMessage(ChatColor.RED + "/mf force join 'игрок' 'фракция2'");
        sender.sendMessage(ChatColor.RED + "/mf force kick (игрок)");
        sender.sendMessage(ChatColor.RED + "/mf force power 'игрок' 'номер'");
        return false;
    }

    private boolean forceSave(CommandSender sender) {
        if (sender.hasPermission("mf.force.save") || sender.hasPermission("mf.force.*") || sender.hasPermission("mf.admin")) {
            sender.sendMessage(ChatColor.GREEN + "Плагин Medieval Factions сохраняет...");
            main.storage.save();
            return true;
        }
        else {
            sender.sendMessage(ChatColor.RED + "Сожалею! Для использования этой команды вам потребуется следующее разрешение: 'mf.force.save'");
            return false;
        }
    }

    private boolean forceLoad(CommandSender sender) {
        if (sender.hasPermission("mf.force.load") || sender.hasPermission("mf.force.*")|| sender.hasPermission("mf.admin")) {
            sender.sendMessage(ChatColor.GREEN + "Плагин Medieval Factions загружается...");
            main.storage.load();
            main.reloadConfig();
            return true;
        }
        else {
            sender.sendMessage(ChatColor.RED + "Сожалею! Для использования этой команды вам потребуется следующее разрешение: 'mf.force.load'");
            return false;
        }
    }

    private boolean forcePeace(CommandSender sender, String[] args) {

        if (sender.hasPermission("mf.force.peace") || sender.hasPermission("mf.force.*")|| sender.hasPermission("mf.admin")) {

            if (args.length >= 4) {

                // get arguments designated by single quotes
                ArrayList<String> singleQuoteArgs = main.utilities.getArgumentsInsideSingleQuotes(args);

                if (singleQuoteArgs.size() < 2) {
                    sender.sendMessage(ChatColor.RED + "Фракции не определены. Обязательно заключать в одинарные кавычки!");
                    return false;
                }

                String factionName1 = singleQuoteArgs.get(0);
                String factionName2 = singleQuoteArgs.get(1);

                Faction faction1 = main.utilities.getFaction(factionName1, main.factions);
                Faction faction2 = main.utilities.getFaction(factionName2, main.factions);

                // force peace
                if (faction1 != null && faction2 != null) {
                    if (faction1.isEnemy(faction2.getName())) {
                        faction1.removeEnemy(faction2.getName());
                    }
                    if (faction2.isEnemy(faction1.getName())) {
                        faction2.removeEnemy(faction1.getName());
                    }

                    // announce peace to all players on server.
                    main.utilities.sendAllPlayersOnServerMessage(ChatColor.GREEN + faction1.getName() + " теперь в мире с " + faction2.getName() + "!");
                    return true;
                }
                else {
                    sender.sendMessage(ChatColor.RED + "Одна из обозначенных фракций не найдена!");
                    return false;
                }
            }

            // send usage
            sender.sendMessage(ChatColor.RED + "Использование: /mf force peace 'фракция-1' 'фракция-2'");
            return false;
        }
        else {
            sender.sendMessage(ChatColor.RED + "Сожалею! Для использования этой команды вам потребуется следующее разрешение: 'mf.force.peace'");
            return false;
        }

    }

    private boolean forceDemote(CommandSender sender, String[] args) { // 1 argument
        if (sender.hasPermission("mf.force.demote") || sender.hasPermission("mf.force.*")|| sender.hasPermission("mf.admin")) {
            if (args.length > 2) {
                String playerName = args[2];
                for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                    if (player.getName().equalsIgnoreCase(playerName)) {
                        for (Faction faction : main.factions) {
                            if (faction.isOfficer(player.getUniqueId())) {
                                faction.removeOfficer(player.getUniqueId());

                                if (player.isOnline()) {
                                    Bukkit.getPlayer(player.getUniqueId()).sendMessage(ChatColor.AQUA + "Вас принудительно понижают в звании офицера фракции " + faction.getName() + "!");
                                }
                            }
                        }
                    }
                }

                sender.sendMessage(ChatColor.GREEN + "Успех! Если игрок считался офицером какой-либо фракции, он больше не им.");
                return true;
            }
            else {
                sender.sendMessage(ChatColor.RED + "Использование: /mf force demote (игрок)");
                return false;
            }
        }
        else {
            sender.sendMessage(ChatColor.RED + "Сожалею! Для использования этой команды вам потребуется следующее разрешение: 'mf.force.demote'");
            return false;
        }
    }

    private boolean forceJoin(CommandSender sender, String[] args) { // 2 arguments
        if (sender.hasPermission("mf.force.join") || sender.hasPermission("mf.force.*")|| sender.hasPermission("mf.admin")) {

            if (args.length >= 4) {

                // get arguments designated by single quotes
                ArrayList<String> singleQuoteArgs = main.utilities.getArgumentsInsideSingleQuotes(args);

                if (singleQuoteArgs.size() < 2) {
                    sender.sendMessage(ChatColor.RED + "Обозначено недостаточно аргументов. Должен быть заключен в одинарные кавычки!");
                    return false;
                }

                String playerName = singleQuoteArgs.get(0);
                String factionName = singleQuoteArgs.get(1);

                Faction faction = main.utilities.getFaction(factionName, main.factions);

                for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                    if (player.getName().equalsIgnoreCase(playerName)) {

                        if (faction != null) {
                            if (!(isInFaction(player.getUniqueId(), main.factions))) {
                                faction.addMember(player.getUniqueId(), getPlayersPowerRecord(player.getUniqueId(), main.playerPowerRecords).getPowerLevel());
                                try {
                                    sendAllPlayersInFactionMessage(faction, ChatColor.GREEN + player.getName() + " присоединился " + faction.getName());
                                } catch (Exception ignored) {

                                }
                                if (player.isOnline()) {
                                    Bukkit.getPlayer(player.getUniqueId()).sendMessage(ChatColor.AQUA + "Вы были вынуждены присоединиться к фракции " + faction.getName() + "!");
                                }
                                sender.sendMessage(ChatColor.GREEN + "Успех! Игрок был вынужден присоединиться к фракции.");
                                return true;
                            }
                            else {
                                sender.sendMessage(ChatColor.RED + "Этот игрок уже состоит во фракции, извините!");
                                return false;
                            }
                        }
                        else {
                            sender.sendMessage(ChatColor.RED + "Эта фракция не найдена!");
                            return false;
                        }
                    }
                }
                sender.sendMessage(ChatColor.RED + "Игрок не найден!");
                return false;
            }

            // send usage
            sender.sendMessage(ChatColor.RED + "Использование: /mf force join 'игрок' 'фракция'");
            return false;
        }
        else {
            sender.sendMessage(ChatColor.RED + "Сожалею! Для использования этой команды вам потребуется следующее разрешение: 'mf.force.join'");
            return false;
        }
    }

    private boolean forceKick(CommandSender sender, String[] args) { // 1 argument
        if (sender.hasPermission("mf.force.kick") || sender.hasPermission("mf.force.*")|| sender.hasPermission("mf.admin")) {
            if (args.length > 2) {
                String playerName = args[2];
                for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                    if (player.getName().equalsIgnoreCase(playerName)) {
                        for (Faction faction : main.factions) {
                            if (faction.isOwner(player.getUniqueId())) {
                                sender.sendMessage(ChatColor.RED + "Невозможно принудительно исключить владельца из его фракции! Попробуйте распустить фракцию!");
                                return false;
                            }

                            if (faction.isMember(player.getUniqueId())) {
                                faction.removeMember(player.getUniqueId(), getPlayersPowerRecord(player.getUniqueId(), main.playerPowerRecords).getPowerLevel());

                                if (player.isOnline()) {
                                    Bukkit.getPlayer(player.getUniqueId()).sendMessage(ChatColor.AQUA + "Вас насильно выгнали из фракции " + faction.getName() + "!");
                                }

                                if (faction.isOfficer(player.getUniqueId())) {
                                    faction.removeOfficer(player.getUniqueId());
                                }
                            }
                        }
                    }
                }

                sender.sendMessage(ChatColor.GREEN + "Успех! Если игрок был во фракции, он больше не член.");
                return true;
            }
            else {
                sender.sendMessage(ChatColor.RED + "Использование: /mf force kick (игрок)");
                return false;
            }
        }
        else {
            sender.sendMessage(ChatColor.RED + "Сожалею! Для использования этой команды вам потребуется следующее разрешение: 'mf.force.kick'");
            return false;
        }
    }

    public boolean forcePower(CommandSender sender, String[] args) {
        if (sender.hasPermission("mf.force.power") || sender.hasPermission("mf.force.*") || sender.hasPermission("mf.admin")) {

            if (args.length >= 4) {

                // get arguments designated by single quotes
                ArrayList<String> singleQuoteArgs = main.utilities.getArgumentsInsideSingleQuotes(args);

                if (singleQuoteArgs.size() < 2) {
                    sender.sendMessage(ChatColor.RED + "Игрок и желаемая сила должны быть указаны в одинарных кавычках!");
                    return false;
                }

                String player = singleQuoteArgs.get(0);
                int desiredPower = -1;

                try {
                    desiredPower = Integer.parseInt(singleQuoteArgs.get(1));
                } catch (Exception e) {
                    sender.sendMessage(ChatColor.RED + "Желаемая мощность должна быть числом!");
                    return false;
                }

                PlayerPowerRecord record = getPlayersPowerRecord(findUUIDBasedOnPlayerName(player), main.playerPowerRecords);

                record.setPowerLevel(desiredPower);
                sender.sendMessage(ChatColor.GREEN + "Уровень мощности '" + player + "'был установлен на " + desiredPower);
                return true;
            }

            // send usage
            sender.sendMessage(ChatColor.RED + "Использование: /mf force power 'игрок' 'номер'");
            return false;
        } else {
            sender.sendMessage(ChatColor.RED + "Sorry! You need the following permission to use this command: 'mf.force.power'");
            return false;
        }
    }

}
