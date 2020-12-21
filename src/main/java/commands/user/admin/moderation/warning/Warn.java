package commands.user.admin.moderation.warning;

import commands.base.Category;
import commands.base.Command;
import commands.base.CommandWithoutSubCommands;
import events.MessageReceivedEvent;

public class Warn extends CommandWithoutSubCommands {
	public Warn(Command superCommand) {
		super(Category.MODERATION
				, "Warn an user."
				, "@CacheUser Reason"
				, true
				, superCommand);
		this.addName("Warn");
		this.addExample("@King Spamming", "Warns the user King for spamming.");
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {

	}
}
