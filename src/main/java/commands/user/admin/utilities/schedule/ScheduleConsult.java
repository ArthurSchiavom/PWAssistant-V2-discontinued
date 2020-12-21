package commands.user.admin.utilities.schedule;

import commands.base.Command;
import commands.base.CommandWithoutSubCommands;
import commands.base.Requirement;
import events.MessageReceivedEvent;
import information.scheduling.messageSchedule.MessagingScheduler;
import information.scheduling.messageSchedule.MessagingSchedulerRegister;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.List;

public class ScheduleConsult extends CommandWithoutSubCommands {
	public ScheduleConsult(Command superCommand) {
		super(superCommand.getCategory()
				, "Consult existing schedules."
				, "ScheduleName"
				, false
				, superCommand);
		this.addName("Consult");
		this.addName("C");
		this.addExample("", "See a short-format information about all schedules.");
		this.addExample("EventReminder", "See information about the schedule EventReminder.");
		this.getRequirementsManager().addRequirement(Requirement.ADMIN);
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		MessageChannel channel = event.getChannel();

		String args = this.extractArgumentsOnly(event.getMessage().getContentRaw());
		String guildId = event.getGuild().getId();
		if (args == null) {
			channel.sendMessage(buildFullList(guildId)).queue();
		}
		else {
			MessagingScheduler messagingScheduler = MessagingSchedulerRegister.getSchedule(guildId, args);

			if (messagingScheduler == null)
				channel.sendMessage("There is no schedule with that name. <:png98:489925231285239830>").queue();
			else
				channel.sendMessage(messagingScheduler.buildFullVisualRepresentation()).queue();
		}
	}

	private String buildFullList(String guildId) {
		List<MessagingScheduler> guildSchedulers = MessagingSchedulerRegister.getGuildSchedules(guildId);
		if (guildSchedulers.isEmpty()) {
			return "No schedules were found in this server <:ffconfused:489925231285239830>";
		}

		StringBuilder sb = new StringBuilder();
		for (MessagingScheduler scheduler : guildSchedulers) {
			sb.append("\n\n").append(scheduler.buildShortVisualRepresentation());
		}
		return sb.toString();
	}
}
