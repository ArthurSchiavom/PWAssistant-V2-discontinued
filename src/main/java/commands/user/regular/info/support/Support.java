package commands.user.regular.info.support;

import commands.base.Category;
import commands.base.CommandWithoutSubCommands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Support extends CommandWithoutSubCommands {
	public Support() {
		super(Category.INFORMATION
				, "Link to my server!"
				, null
				, false);
		this.addName("Support");
		this.addName("Feedback");
		this.addName("News");
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		event.getChannel().sendMessage(
				"Need support, want to give feedback or find news about the bot? " +
						"Join https://discord.gg/tRfrSdP !").queue();
	}
}
