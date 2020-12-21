package commands.user.owner.utils.manageServers;

import commands.base.Category;
import commands.base.Command;
import commands.base.CommandWithoutSubCommands;
import commands.base.Requirement;
import events.MessageReceivedEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;
import utils.Utils;

import java.util.List;
import java.util.Queue;

public class Status extends CommandWithoutSubCommands {
	private final static String ARG_COUNT = "count";
	private final static String ARG_FULL = "full";
	public Status(Command superCommand) {
		super(Category.UTILITY
				, "View the total amount of servers that the bot is in and total amount of users."
				, "total|list"
				, true
				, superCommand);
		this.addName("Status");
		this.addName("S");
		this.addExample(ARG_COUNT, "Shows the total count of servers and users.");
		this.addExample("", "Same as " + ARG_COUNT + ".");
		this.addExample(ARG_FULL, "Lists the servers, displaying their ID (used to force the bot to leave a guild).");
		this.getRequirementsManager().addRequirement(Requirement.OWNER);
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		JDA jda = event.getJda();
		MessageChannel channel = event.getChannel();
		String args = this.extractArgumentsOnly(event.getMessage().getContentRaw());
		if (args == null)
			args = ARG_COUNT;
		String messageString;
		switch (args.toLowerCase()) {
			case ARG_FULL:
				messageString = buildFullDisplay(jda);
				break;
			case ARG_COUNT:
			default:
				messageString = buildCountDisplay(jda);

		}
		Queue<Message> messageQueue = new MessageBuilder(messageString).buildAll(MessageBuilder.SplitPolicy.NEWLINE);
		Message msg = messageQueue.poll();
		while (msg != null) {
			channel.sendMessage(msg).queue();
			msg = messageQueue.poll();
		}
	}

	private String buildFullDisplay(JDA jda) {
		StringBuilder sb = new StringBuilder();
		List<Guild> guilds = jda.getGuilds();
		for (Guild guild : guilds) {
			List<Member> guildMembers = guild.getMembers();
			int nMembersNotOflline = Utils.filterMembersByStatus(guildMembers, true, OnlineStatus.ONLINE
					, OnlineStatus.DO_NOT_DISTURB, OnlineStatus.IDLE
					, OnlineStatus.INVISIBLE, OnlineStatus.UNKNOWN).size();
			sb.append("\n\n**").append(guild.getName()).append("** is owned by **")
					.append(guild.getOwner().getUser().getAsTag()).append("**, has **")
					.append(guildMembers.size()).append("** members (**")
					.append(nMembersNotOflline).append("** non-bots online)")
					.append(" and it's ID is ")
					.append(guild.getId()).append(".");
		}
		return sb.toString();
	}

	private String buildCountDisplay(JDA jda) {
		StringBuilder sb = new StringBuilder();
		List<Guild> guilds = jda.getGuilds();
		List<User> users = jda.getUsers();
		int nMembersNotOffline = Utils.getAllMembersOfStatus(false, true, OnlineStatus.ONLINE
				, OnlineStatus.DO_NOT_DISTURB, OnlineStatus.IDLE
				, OnlineStatus.INVISIBLE, OnlineStatus.UNKNOWN).size();
		sb.append("I'm in **").append(guilds.size()).append("** guilds, serving **")
				.append(users.size()).append("** users (**").append(nMembersNotOffline).append("** non-bots online)!");
		return sb.toString();
	}
}
