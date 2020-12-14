package de.nicki.commands;


import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;

public class ClearCommand implements ServerCommand{
	
	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		
		if(!m.hasPermission(channel, Permission.MESSAGE_MANAGE)) {
			channel.sendMessage("`Du hast nicht die n�tigen Rechte um diesen Command auszuf�hren`").queue();
		}
		
		if(m.hasPermission(channel, Permission.MESSAGE_MANAGE)) {
		//message.delete().queue();
		String [] args = message.getContentDisplay().split(" ");
		
			// !clear 3
			if (args.length==2) {
				try {
					int amount = Integer.parseInt(args[1]);
					channel.purgeMessages(get(channel,amount));
					channel.sendMessage("`" + amount + " Nachrichten gel�scht.`").complete().delete().queueAfter(3, TimeUnit.SECONDS);
					return;
				}
				catch(NumberFormatException e){
					e.printStackTrace();
				}
			}
		}
	}

	public List<Message> get(MessageChannel channel, int amount){
		List<Message> messages = new ArrayList<>();
		int i = amount;
		
		for(Message message : channel.getIterableHistory().cache(false)) {
			if(!message.isPinned()) {
				messages.add(message);
			}
			if(i-- <= 0) break;
		}
		
		return messages;
		
	}
}
