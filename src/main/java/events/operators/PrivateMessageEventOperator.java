package events.operators;

import events.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import questionnaire.base.QuestionnaireRegister;

public class PrivateMessageEventOperator {
	public void processMessageReceived(PrivateMessageReceivedEvent event) {
		if (event.getAuthor().isBot())
			return;

		MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent(event);
		QuestionnaireRegister.getInstance().processPossibleReply(messageReceivedEvent);
	}
}
