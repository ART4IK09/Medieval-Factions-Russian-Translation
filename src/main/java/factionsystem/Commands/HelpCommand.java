package factionsystem.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HelpCommand {

    // Each page of the help command should have a title and nine commands. This is for ease of use.
    public boolean sendHelpMessage(CommandSender sender, String[] args) {

        if (args.length == 1 || args.length == 0) {
            sendPageOne(sender);
        }

        if (args.length > 1) {
            if (args[1].equalsIgnoreCase("1")) {
                sendPageOne(sender);
            }
            if (args[1].equalsIgnoreCase("2")) {
                sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "\n == Фракции 2/6 == " + "\n");
                sender.sendMessage(ChatColor.YELLOW + "/mf kick - Выгонить игрока из своей фракции. " + "\n");
                sender.sendMessage(ChatColor.YELLOW + "/mf transfer - Передать право собственности на свою фракцию другому игроку.\n");
                sender.sendMessage(ChatColor.YELLOW + "/mf disband - Распустить свою фракцию (создатель)." + "\n");
                sender.sendMessage(ChatColor.YELLOW + "/mf declarewar - Объявить войну другой фракции." + "\n");
                sender.sendMessage(ChatColor.YELLOW + "/mf makepeace - Отправить предложение мира другой фракции." + "\n");
                sender.sendMessage(ChatColor.YELLOW + "/mf claim - Заявить права на землю для своей фракции." + "\n");
                sender.sendMessage(ChatColor.YELLOW + "/mf unclaim - Отказатся от земли для своей фракции." + "\n");
                sender.sendMessage(ChatColor.YELLOW + "/mf unclaimall - Отказатся от всей земли для своей фракции." + "\n");
                sender.sendMessage(ChatColor.YELLOW + "/mf checkclaim - Проверить, заявлены ли права на землю." + "\n");
            }
            if (args[1].equalsIgnoreCase("3")) {
                sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "\n == Фракции 3/6 == " + "\n");
                sender.sendMessage(ChatColor.YELLOW + "/mf autoclaim - Включить автоматическое требование, чтобы упростить получение земли." + "\n");
                sender.sendMessage(ChatColor.YELLOW + "/mf promote - Повысить игрока до статуса офицера." + "\n");
                sender.sendMessage(ChatColor.YELLOW + "/mf demote - Понизить офицера до статуса члена." + "\n");
                sender.sendMessage(ChatColor.YELLOW + "/mf power - Проверить свой уровень мощности." + "\n");
                sender.sendMessage(ChatColor.YELLOW + "/mf sethome - Установить свою фракцию домом." + "\n");
                sender.sendMessage(ChatColor.YELLOW + "/mf home - Телепортироватся в дом вашей фракции." + "\n");
                sender.sendMessage(ChatColor.YELLOW + "/mf who - Просмотр информации о фракции конкретного игрока." + "\n");
                sender.sendMessage(ChatColor.YELLOW + "/mf ally - Попытатся вступить в союз с фракцией." + "\n");
                sender.sendMessage(ChatColor.YELLOW + "/mf breakalliance - Разорвать союз с фракцией." + "\n");
            }
            if (args[1].equalsIgnoreCase("4")) {
                sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "\n == Фракции 4/6 == " + "\n");
                sender.sendMessage(ChatColor.YELLOW + "/mf rename - Переименовать свою фракцию" + "\n");
                sender.sendMessage(ChatColor.YELLOW + "/mf lock - Заприте сундук или дверь." + "\n");
                sender.sendMessage(ChatColor.YELLOW + "/mf unlock Разблокировать сундук или дверь." + "\n");
                sender.sendMessage(ChatColor.YELLOW + "/mf grantaccess - Предоставить кому-либо доступ к заблокированному блоку.");
                sender.sendMessage(ChatColor.YELLOW + "/mf checkaccess - Проверить, у кого есть доступ к заблокированному блоку.");
                sender.sendMessage(ChatColor.YELLOW + "/mf revokeaccess - Отменить чей-либо доступ к заблокированному блоку.");
                sender.sendMessage(ChatColor.YELLOW + "/mf laws - Ознакомьтесь с законами вашей фракции.");
                sender.sendMessage(ChatColor.YELLOW + "/mf addlaw - Добавьте закон в свою фракцию.");
                sender.sendMessage(ChatColor.YELLOW + "/mf removelaw - Удалите закон из своей фракции.");
            }
            if (args[1].equalsIgnoreCase("5")) {
                sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "\n == Фракции 5/6 == " + "\n");
                sender.sendMessage(ChatColor.YELLOW + "/mf editlaw - Отредактируйте уже существующий закон в вашей фракции.");
                sender.sendMessage(ChatColor.YELLOW + "/mf chat - Переключить чат фракции.");
                sender.sendMessage(ChatColor.YELLOW + "/mf vassalize - Предложить вассализировать фракцию.");
                sender.sendMessage(ChatColor.YELLOW + "/mf swearfealty - Поклянись в верности фракции.");
                sender.sendMessage(ChatColor.YELLOW + "/mf declareindependence - Объявите независимость от вашего сеньора.");
                sender.sendMessage(ChatColor.YELLOW + "/mf grantindependence - Предоставить вассальную независимость от действий.");
		        sender.sendMessage(ChatColor.GOLD + "/mf gate create (<обязательно>имя) - Создать врата");
		        sender.sendMessage(ChatColor.GOLD + "/mf gate name (<обязательно>имя) - Переименовать врата");
		        sender.sendMessage(ChatColor.GOLD + "/mf gate list - Список врат в вашей фракции");
		        sender.sendMessage(ChatColor.GOLD + "/mf gate remove - Убрать врата");
		        sender.sendMessage(ChatColor.GOLD + "/mf gate cancel - Отмена действия(связаного с вратами)");
            }
            if (args[1].equalsIgnoreCase("6")) {
                sender.sendMessage(ChatColor.BOLD + "" + ChatColor.AQUA + "\n == Фракции 6/6 == " + "\n");
		        sender.sendMessage(ChatColor.GOLD + "/mf duel challenge (игрок) (время) - Начать дуель");
		        sender.sendMessage(ChatColor.GOLD + "/mf duel accept (игрок) - Принять приглашение в дуель");
		        sender.sendMessage(ChatColor.GOLD + "/mf duel cancel - Отмена текущего действия");
                sender.sendMessage(ChatColor.YELLOW + "/mf bypass - Обход защиты.");
                sender.sendMessage(ChatColor.YELLOW + "/mf config show - Показать значения конфигурации.");
                sender.sendMessage(ChatColor.YELLOW + "/mf config set (функция) (значение) - Установите значение конфигурации.");
                sender.sendMessage(ChatColor.YELLOW + "/mf force - Заставить плагин выполнять определенные действия." + "\n");
                sender.sendMessage(ChatColor.YELLOW + "/mf version - Проверить версию плагина." + "\n");
                sender.sendMessage(ChatColor.YELLOW + "/mf resetpowerlevels - Сбросить рекорды силы игроков и уровни совокупной силы фракций." + "\n");
            }
        }
        return true;
    }

    static void sendPageOne(CommandSender sender) {
        sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "\n == Фракции 1/6 == " + "\n");
        sender.sendMessage(ChatColor.YELLOW + "/mf help # - Список всех команд." + "\n");
        sender.sendMessage(ChatColor.YELLOW + "/mf list - Список фракций на сервере." + "\n");
        sender.sendMessage(ChatColor.YELLOW + "/mf info - Посмотреть информацию про свою или чужую фракцию." + "\n");
        sender.sendMessage(ChatColor.YELLOW + "/mf members - Список членов вашей фракции или другой фракции." + "\n");
        sender.sendMessage(ChatColor.YELLOW + "/mf join - Присоединяйтесь к фракции, если вас пригласили." + "\n");
        sender.sendMessage(ChatColor.YELLOW + "/mf leave - Оставьте свою фракцию." + "\n");
        sender.sendMessage(ChatColor.YELLOW + "/mf create - Создать новую фракцию." + "\n");
        sender.sendMessage(ChatColor.YELLOW + "/mf invite - Пригласите игрока в свою фракцию." + "\n");
        sender.sendMessage(ChatColor.YELLOW + "/mf desc - Установите описание вашей фракции." + "\n");
    }
}
