package commands.user.regular.info.donate;

import commands.base.Category;
import commands.base.CommandWithoutSubCommands;
import events.MessageReceivedEvent;

public class Donate extends CommandWithoutSubCommands {
	public Donate() {
		super(Category.INFORMATION
				, "Information about donation."
				, null
				, false);
		this.addName("Donate");
		this.addName("Donations");
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		event.getChannel().sendMessage(
				"Soon a Patreon page will be set but if you are looking to do a " +
						"one-time donation you can use <https://www.paypal.me/ArthurlKON>.").queue();
	}
}
