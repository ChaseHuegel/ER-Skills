package er.seven.skills;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.maxlego08.discord.api.Bot;

public class DiscordListener implements Listener
{
	public Bot bot;
	
	public DiscordListener()
	{
		bot = new Bot().create("MzQ5NzMzMTE1MDc5NTU3MTIw.XwF5Xw.Xc9rdB7gLkxytr-ch5V5TirWycI").setGame("Minecraft").complete();
		bot.getJda().addEventListener(new JDAListener());
	}
	
	public void sendMessage(String message)
	{
		for (int i = 0; i < bot.getChannels().size(); i++)
		{
			if (bot.getChannels().get(i).getName().equalsIgnoreCase("minecraft"))
			{
				bot.sendMessage(bot.getChannels().get(i).getIdLong(), message);
				break;
			}
		}
	}
	
	public void sendFeed(String message)
	{
		for (int i = 0; i < bot.getChannels().size(); i++)
		{
			if (bot.getChannels().get(i).getName().equalsIgnoreCase("mc-server-feed"))
			{
				bot.sendMessage(bot.getChannels().get(i).getIdLong(), message);
				break;
			}
		}
	}
	
	@EventHandler
    public void onJoin(PlayerJoinEvent event) 
	{
		sendFeed(event.getPlayer().getName() + " joined the game.");
	}
	
	@EventHandler
    public void onLeave(PlayerQuitEvent event) 
	{
		sendFeed(event.getPlayer().getName() + " left the game.");
	}
	
	@EventHandler
    public void onChat(AsyncPlayerChatEvent event) 
	{
		sendFeed(event.getPlayer().getName() + ": "  + event.getMessage());
	}
}
