package er.seven.skills;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class JDAListener extends ListenerAdapter
{
	@Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        Message msg = event.getMessage();
        MessageChannel channel = event.getChannel();
        if (channel.getName().equalsIgnoreCase("mc-server-feed") && msg.getAuthor().isBot() == false)
        {
            Main.Instance().getServer().broadcastMessage( msg.getAuthor().getName() + ": " + msg.getContentDisplay() );
        }
    }
}
