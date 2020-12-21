package questionnaire.base.question;

import events.MessageReceivedEvent;

public interface ReplyProcessorFunc {
	/**
	 * Processes a reply.
	 *
	 * @param event The event containing the reply.
	 */
	public void process(MessageReceivedEvent event);
}
