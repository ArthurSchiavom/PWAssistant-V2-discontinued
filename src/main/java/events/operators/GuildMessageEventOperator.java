package events.operators;

import commands.CommandExecutor;
import information.triggers.TriggerRegister;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
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
	public void processMessageReceived(MessageReceivedEvent event) {
		if (event.getAuthor().isBot())
			return;

		GuildMessageDeleteEventOperator.storeMessage(event.getMessage());

		if (!QuestionnaireRegister.getInstance().processPossibleReply(event)) {
			CommandExecutor.getInstance().executePossibleCommandRequest(event);
		}
		TriggerRegister.getInstance().processEvent(event);
	}
}
