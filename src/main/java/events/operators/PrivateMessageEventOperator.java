package events.operators;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import questionnaire.base.QuestionnaireRegister;

public class PrivateMessageEventOperator {
	public void processMessageReceived(MessageReceivedEvent event) {
		if (event.getAuthor().isBot())
			return;

		QuestionnaireRegister.getInstance().processPossibleReply(event);
	}
}
