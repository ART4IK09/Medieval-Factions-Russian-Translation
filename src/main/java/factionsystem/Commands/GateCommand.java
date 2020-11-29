package factionsystem.Commands;

import static factionsystem.Subsystems.UtilitySubsystem.createStringFromFirstArgOnwards;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import factionsystem.Main;
import factionsystem.Objects.Faction;
import factionsystem.Objects.Gate;
import factionsystem.Subsystems.UtilitySubsystem;

public class GateCommand {

	Main main = null;
	
	public GateCommand(Main plugin)
	{
		main = plugin;
	}
	
	public void handleGate(CommandSender sender, String[] args)
	{
		if (sender instanceof Player)
		{
			Player player = (Player) sender;
			
			if (player.hasPermission("mf.gate") || player.hasPermission("mf.default"))
			{
				if (args.length > 1)
				{
					if (args[1].equalsIgnoreCase("cancel"))
					{
						if (main.creatingGatePlayers.containsKey(player.getUniqueId()))
						{
							main.creatingGatePlayers.remove(player.getUniqueId());
							player.sendMessage(ChatColor.RED + "Создание ворот отменено!");
							return;
						}
					}
					if (args[1].equalsIgnoreCase("create"))
					{
						if (main.creatingGatePlayers.containsKey(player.getUniqueId()))
						{
							main.creatingGatePlayers.remove(player.getUniqueId());
							player.sendMessage(ChatColor.RED + "Вы уже создаете ворота!");
							return;
						}
						else
						{
							Faction faction = UtilitySubsystem.getPlayersFaction(player.getUniqueId(), main.factions);
							if (faction != null)
							{
								if (faction.isOfficer(player.getUniqueId()) || faction.isOwner(player.getUniqueId()))
								{
				        			String gateName = "Unnamed Gate";
				        			if (args.length > 2)
				        			{
				        				gateName = UtilitySubsystem.createStringFromArgIndexOnwards(2, args);
				        			}
									UtilitySubsystem.startCreatingGate(main, player, gateName);
									//TODO: Config setting for magic gate tool.
									player.sendMessage(ChatColor.AQUA + "Создание ворот '" + gateName + "'.\nНажмите на блок с золотой мотыгой, чтобы выбрать первую точку..");
									return;
								}
								else
								{
									player.sendMessage(ChatColor.RED + "Вы должны быть офицером фракции или владельцем, чтобы использовать эту команду.");
									return;
								}
							}
						}
					}
					else if (args[1].equalsIgnoreCase("list"))
					{
						Faction faction = UtilitySubsystem.getPlayersFaction(player.getUniqueId(), main.factions);
						if (faction != null)
						{
							if (faction.getGates().size() > 0)
							{
								player.sendMessage(ChatColor.AQUA + "Врата фракций");
								for (Gate gate : faction.getGates())
								{
									player.sendMessage(ChatColor.AQUA + String.format("%s: %s", gate.getName(), gate.coordsToString()));
								}
							}
							else
							{
								player.sendMessage(ChatColor.RED + "У вашей фракции нет ворот.");
								return;
							}
						}
						else
						{
							player.sendMessage(ChatColor.RED + String.format("Вы не состоите ни в какой фракции."));
							return;
						}
					}
					else if (args[1].equalsIgnoreCase("remove"))
					{
						if (player.getTargetBlock(null, 16) != null)
						{
							if (UtilitySubsystem.isGateBlock(player.getTargetBlock(null, 16), main.factions))
							{
								Gate gate = UtilitySubsystem.getGate(player.getTargetBlock(null, 16), main.factions);
								Faction faction = UtilitySubsystem.getGateFaction(gate, main.factions);
								if (faction != null)
								{
									if (faction.isOfficer(player.getUniqueId()) || faction.isOwner(player.getUniqueId()))
									{
										faction.removeGate(gate);
										player.sendMessage(ChatColor.AQUA + String.format("Удалены ворота'%s'.", gate.getName()));
										return;
									}
									else
									{
										player.sendMessage(ChatColor.RED + "Вы должны быть офицером фракции или владельцем, чтобы использовать эту команду.");
										return;
									}
								}
								else
								{
									player.sendMessage(ChatColor.RED + String.format("Error: Не удалось найти фракцию ворот.", gate.getName()));
									return;
								}
							}
							else
							{
								player.sendMessage(ChatColor.RED + "Целевой блок не является частью ворот.");
								return;
							}
						}
						else
						{
							player.sendMessage(ChatColor.RED + "Блок не обнаружен для проверки ворот.");
							return;
						}
					}
					else if (args[1].equalsIgnoreCase("name"))
					{						
						if (player.getTargetBlock(null, 16) != null)
						{
							if (UtilitySubsystem.isGateBlock(player.getTargetBlock(null, 16), main.factions))
							{
								Gate gate = UtilitySubsystem.getGate(player.getTargetBlock(null, 16), main.factions);
								if (args.length > 2)
								{
									Faction faction = UtilitySubsystem.getGateFaction(gate, main.factions);
									if (faction != null)
									{
										if (faction.isOfficer(player.getUniqueId()) || faction.isOwner(player.getUniqueId()))
										{
											String name = UtilitySubsystem.createStringFromArgIndexOnwards(2, args);
											gate.setName(name);
											player.sendMessage(ChatColor.AQUA + String.format("Название ворот изменено на '%s'.", gate.getName()));
											return;
										}
										else
										{
											player.sendMessage(ChatColor.RED + "Вы должны быть офицером фракции или владельцем, чтобы использовать эту команду.");
											return;
										}
									}
									else
									{
										player.sendMessage(ChatColor.RED + "Error: Не удалось найти фракцию ворот.");
										return;
									}
								}
								else
								{
									player.sendMessage(ChatColor.AQUA + String.format("Это %s' врата", gate.getName()));
									return;
								}
							}
							else
							{
								player.sendMessage(ChatColor.RED + "Целевой блок не является частью ворот.");
								return;
							}
						}
						else
						{
							player.sendMessage(ChatColor.RED + "Блок не обнаружен для проверки ворот.");
							return;
						}
					}						
				}
				else
				{
			        sender.sendMessage(ChatColor.RED + "Подкоманды:");
			        sender.sendMessage(ChatColor.AQUA + "/mf gate create (<важно>имя)");
			        sender.sendMessage(ChatColor.RED + "/mf gate name (<важно>имя)");
			        sender.sendMessage(ChatColor.RED + "/mf gate list");
			        sender.sendMessage(ChatColor.RED + "/mf gate remove");
			        sender.sendMessage(ChatColor.RED + "/mf gate cancel");
			        return;
				}
			}
            else {
                player.sendMessage(ChatColor.RED + "Сожалею! Для использования этой команды вам необходимо следующее разрешение: 'mf.gate'");
            }

		}
	}
	
}
