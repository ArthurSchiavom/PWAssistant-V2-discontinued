package commands.user.admin.utilities.trigger;

import commands.base.Command;
import commands.base.CommandWithoutSubCommands;
import commands.base.Requirement;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import questionnaire.trigger.AddTriggerQuestionnaire;

public class TriggerAdd extends CommandWithoutSubCommands {
	public TriggerAdd(Command superCommand) {
		super(superCommand.getCategory()
				, "Add a new trigger."
				, null
				, false
				, superCommand);
		this.addName("Add");
		this.addName("A");
		this.getRequirementsManager().addRequirement(Requirement.ADMIN);
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		new AddTriggerQuestionnaire().startQuestionnaire(event.getChannel(), event.getAuthor().getIdLong());
	}
}
