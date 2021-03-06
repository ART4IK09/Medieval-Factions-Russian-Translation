package factionsystem.EventHandlers;

import java.time.ZonedDateTime;

import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerQuitEvent;

import factionsystem.Main;
import factionsystem.Objects.PlayerActivityRecord;
import factionsystem.Objects.PlayerPowerRecord;
import factionsystem.Subsystems.UtilitySubsystem;

public class PlayerLeaveEventHandler {

	Main main = null;
	
	public PlayerLeaveEventHandler(Main plugin)
	{
		main = plugin;
	}
	
	public void handle(PlayerQuitEvent event)
	{
		if (main.lockingPlayers.contains(event.getPlayer().getUniqueId()))
		{
			main.lockingPlayers.remove(event.getPlayer().getUniqueId());
		}
		if (main.unlockingPlayers.contains(event.getPlayer().getUniqueId()))
		{
			main.unlockingPlayers.remove(event.getPlayer().getUniqueId());
		}
		if (main.playersGrantingAccess.containsKey(event.getPlayer().getUniqueId()))
		{
			main.playersGrantingAccess.remove(event.getPlayer().getUniqueId());
		}
		if (main.playersCheckingAccess.contains(event.getPlayer().getUniqueId()))
		{
			main.playersCheckingAccess.remove(event.getPlayer().getUniqueId());
		}
		if (main.playersRevokingAccess.containsKey(event.getPlayer().getUniqueId()))
		{
			main.playersRevokingAccess.remove(event.getPlayer().getUniqueId());
		}
			
    	PlayerActivityRecord record = UtilitySubsystem.getPlayerActivityRecord(event.getPlayer().getUniqueId(), main.playerActivityRecords);
    	if (record != null)
    	{
    		record.setLastLogout(ZonedDateTime.now());
    	}
	}
	
}
