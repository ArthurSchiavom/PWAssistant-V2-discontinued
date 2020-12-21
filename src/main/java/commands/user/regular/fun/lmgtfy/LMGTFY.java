package commands.user.regular.fun.lmgtfy;

import commands.base.Category;
import commands.base.CommandWithoutSubCommands;
import events.MessageReceivedEvent;

public class LMGTFY extends CommandWithoutSubCommands {
	public LMGTFY() {
		super(Category.FUN
				, "Tell someone that they should have googled it."
				, "What to search"
				, false);
		this.addName("lmgtfy");
		this.addName("lmg");
		this.addExample("court poet", "Tell someone that they should have searched \"court poet\" on google.");
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		String args = this.extractArgumentsOnly(event.getMessage().getContentDisplay());
		if (args == null) {
			event.getChannel().sendMessage("You must tell me what to search for.").queue();
			return;
		}

		args = args.replace(' ', '+');
		event.getChannel().sendMessage("<http://lmgtfy.com/?q=" + args + ">").queue();
	}
}
