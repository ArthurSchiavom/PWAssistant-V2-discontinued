package commands.user.admin.utilities.schedule;

import commands.base.Command;
import commands.base.CommandWithoutSubCommands;
import commands.base.Requirement;
import events.MessageReceivedEvent;
import questionnaire.messagingSchedule.AddMessagingScheduleQuestionnaire;

public class ScheduleAdd extends CommandWithoutSubCommands {
	public ScheduleAdd(Command superCommand) {
		super(superCommand.getCategory()
				, "Add a new messaging schedule!"
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
		new AddMessagingScheduleQuestionnaire().startQuestionnaire(event.getChannel(), event.getAuthor().getIdLong());
	}
}
