package commands.user.admin.utilities.trigger;

import commands.base.Command;
import commands.base.CommandWithoutSubCommands;
import commands.base.Requirement;
import events.MessageReceivedEvent;
import information.CacheModificationSuccessState;
import information.triggers.TriggerRegister;

public class TriggerRemove extends CommandWithoutSubCommands {
	public TriggerRemove(Command superCommand) {
		super(superCommand.getCategory()
				, "Remove a trigger."
				, "TriggerName"
				, true
				, superCommand);
		this.addName("Remove");
		this.addName("R");
		this.getRequirementsManager().addRequirement(Requirement.ADMIN);
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		String args = this.extractArgumentsOnly(event.getMessage().getContentRaw());
		if (args == null) {
			event.getChannel().sendMessage("You need to tell me the name of the trigger to remove!").queue();
			return;
		}

		CacheModificationSuccessState successState = TriggerRegister.getInstance().unregister(event.getGuild().getIdLong(), args);
		String reply;
		switch (successState) {
			case SUCCESS:
				reply = "Trigger removed successfully!";
				break;
			case FAILED_CACHE_MODIFICATION:
				reply = "No trigger named **" + args + "** was found.";
				break;
			case FAILED_DATABASE_MODIFICATION:
				reply = "Oh no! I failed to access my database :worried:\nIf this error persists, please report it in the support server.";
				break;
			default:
				reply = "An unknown error occurred.";
		}
		event.getChannel().sendMessage(reply).queue();
	}
}
