package commands.user.owner.utils.manageServers;

import commands.base.Category;
import commands.base.Command;
import commands.base.CommandWithoutSubCommands;
import commands.base.Requirement;
import events.MessageReceivedEvent;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;

public class Quit extends CommandWithoutSubCommands {
	public Quit(Command superCommand) {
		super(Category.UTILITY
				, "Have me quit a server."
				, "ServerID"
				, true
				, superCommand);
		this.addName("Quit");
		this.getRequirementsManager().addRequirement(Requirement.OWNER);
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		MessageChannel channel = event.getChannel();
		String args = this.extractArgumentsOnly(event.getMessage().getContentRaw());
		if (args == null) {
			channel.sendMessage("Where did you forget the server ID? You need to give it to me.").queue();
			return;
		}
		else {
			try {
				Guild guild = event.getJda().getGuildById(args);
				String guildName = guild.getName();
				guild.leave().complete();
				channel.sendMessage("Left **" + guildName + "**").queue();
			} catch (Exception e) {
				channel.sendMessage("Failed to leave the guild. Is the ID correct?").queue();
			}
		}
	}
}
