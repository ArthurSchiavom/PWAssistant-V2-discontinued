package events.operators;

import commands.CommandExecutor;
import events.EventsManager;
import events.MessageReceivedEvent;
import information.triggers.TriggerRegister;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import questionnaire.base.QuestionnaireRegister;

/**
 * Guild message events processor
 */
public class GuildMessageEventOperator {

	/**
	 * Processes a GuildMessageReceivedEvent event.
	 *
	 * @param event The event to process.
	 */
	public void processMessageReceived(GuildMessageReceivedEvent event) {
		if (event.getAuthor().isBot())
			return;

		GuildMessageDeleteEventOperator.storeMessage(event.getMessage());

		MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent(event);
		if (!QuestionnaireRegister.getInstance().processPossibleReply(messageReceivedEvent)) {
			CommandExecutor.getInstance().executePossibleCommandRequest(messageReceivedEvent);
		}
		TriggerRegister.getInstance().processEvent(messageReceivedEvent);
	}
}
