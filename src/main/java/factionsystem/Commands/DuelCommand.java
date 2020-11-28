package factionsystem.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import factionsystem.Main;
import factionsystem.Objects.Duel;
import factionsystem.Subsystems.UtilitySubsystem;

public class DuelCommand {
	Main main = null;
	
	public DuelCommand(Main plugin) {
		main = plugin;
	}
	
	public void handleDuel(CommandSender sender, String[] args) {
		if (sender instanceof Player)
		{
			Player player = (Player) sender;
			
			if (player.hasPermission("mf.duel") || player.hasPermission("mf.default"))
			{
				if (args.length > 1)
				{
					if (args[1].equalsIgnoreCase("challenge"))
					{
						if (args.length > 2)
						{
							if (args[2].equalsIgnoreCase(player.getName()))
							{
								player.sendMessage(ChatColor.RED + "Вы не можете сражаться с собой!");
								return;
							}
							if (UtilitySubsystem.isDuelling(player, main))
							{
								player.sendMessage(ChatColor.RED + "Вы уже сражаетесь с кем-то!");
								return;
							}
							Player target = Bukkit.getServer().getPlayer(args[2]);
							if (target != null)
							{
								if (!UtilitySubsystem.isDuelling(target, main))
								{
									int timeLimit = 120; // Time limit in seconds. TODO: Make config option.
									if (args.length == 4)
									{
										timeLimit = Integer.parseInt(args[3]);
									}
									UtilitySubsystem.inviteDuel(player, target, timeLimit, main);
									player.sendMessage(ChatColor.AQUA + "Вы бросили вызов " + target.getName() + ");
									return;
								}
								else
								{
									player.sendMessage(ChatColor.RED + target.getName() + " уже на дуэли");
									return;
								}
							}
							else
							{
								player.sendMessage(ChatColor.RED + "Не удалось найти ни одного игрока с именем '" + args[2] + "'.");
								return;
							}
						}
					}
					else if (args[1].equalsIgnoreCase("accept"))
					{
						if (UtilitySubsystem.isDuelling(player, main))
						{
							player.sendMessage(ChatColor.RED + "Вы уже сражаетесь с кем-то!");
							return;
						}
						// If a name is specified to accept the challenge from, look for that specific name.
						if (args.length > 2)
						{
		                	Player challenger = Bukkit.getServer().getPlayer(args[2]);
		                	Duel duel = UtilitySubsystem.getDuel(challenger, player, main);
		                	if (duel != null)
		                	{
		                		if (duel.getStatus().equals(Duel.DuelState.DUELLING))
		                		{
									player.sendMessage(ChatColor.RED + "Вы уже на дуэли " + args[2] + "!");
									return;
		                		}
		                		if (duel.isChallenged(player))
		                		{
		                			duel.acceptDuel();
		                		}
		                		else
		                		{
									player.sendMessage(ChatColor.RED + "Вы не были вызваны на дуэль" + args[2] + "'.");
									return;	
		                		}
		                	}
		                	else
		                	{
								player.sendMessage(ChatColor.RED + "Тебя не вызывали на дуэль." + args[2] + "'.");
								return;
		                	}
						}
						else
						{
		                	Duel duel = UtilitySubsystem.getDuel(player, main);
		                	if (duel != null)
		                	{
		                		if (duel.getStatus().equals(Duel.DuelState.DUELLING))
		                		{
									player.sendMessage(ChatColor.RED + "Вы уже на дуэли!");
									return;
		                		}
		                		if (duel.isChallenged(player))
		                		{
		                			duel.acceptDuel();
		                		}
		                		else
		                		{
									player.sendMessage(ChatColor.RED + "Вас никто не вызывал на дуэль.");
									return;	
		                		}
		                	}
		                	else
		                	{
								player.sendMessage(ChatColor.RED + "Вас никто не вызывал на дуэль.");
								return;
		                	}
						}
					}
					else if (args[1].equalsIgnoreCase("cancel"))
					{
		                if (UtilitySubsystem.isDuelling(player, main))
		                {
		                	Duel duel = UtilitySubsystem.getDuel(player, main);
		                	if (duel != null)
		                	{
		                		if (duel.getStatus().equals(Duel.DuelState.DUELLING))
		                		{
									player.sendMessage(ChatColor.RED + "Невозможно отменить активную дуэль.");
									return;
		                		}
		                		else
		                		{
		                			main.duelingPlayers.remove(duel);
									player.sendMessage(ChatColor.AQUA + "Вызов дуэли отменен.");
									return;
		                		}
		                	}
		                	else
		                	{
								player.sendMessage(ChatColor.AQUA + "У вас нет незавершенных задач, которые нужно отменить.");
								return;
		                	}
		                }
	                	else
	                	{
							player.sendMessage(ChatColor.AQUA + "У вас нет незавершенных задач, которые нужно отменить.");
							return;
	                	}

					}
					else
					{
				        sender.sendMessage(ChatColor.RED + "Команды:");
				        sender.sendMessage(ChatColor.RED + "/mf duel challenge (игрок)");
				        sender.sendMessage(ChatColor.RED + "/mf duel accept (<необязательный> игрок)");
				        sender.sendMessage(ChatColor.RED + "/mf duel cancel");
					}
				}
				else
				{
			        sender.sendMessage(ChatColor.RED + "Комманды:");
			        sender.sendMessage(ChatColor.RED + "/mf duel challenge (игрок) (<необязательно> ограничение по времени в секундах)");
			        sender.sendMessage(ChatColor.RED + "/mf duel accept (<необязательный> игрок)");
			        sender.sendMessage(ChatColor.RED + "/mf duel cancel");
				}
			}
		}
	}
}
