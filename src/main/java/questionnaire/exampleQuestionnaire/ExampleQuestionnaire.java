package questionnaire.exampleQuestionnaire;

import questionnaire.base.Questionnaire;
import questionnaire.base.WhenToDeleteMessages;
import questionnaire.base.WhichMessagesToDelete;

/**
 * Questionnaire to announce a message.
 */
public class ExampleQuestionnaire extends Questionnaire {

	private String title;
	public ExampleQuestionnaire() {
		this.addQuestion("What's the announcement's title?"
				, event -> {
					this.queueMessageToDeleteEnd(event.getMessage());
					title = event.getMessage().getContentRaw();
					this.nextQuestion();
					this.queueMessageToDeleteEnd(event.getMessage());
					this.queueMessageToDeleteNextQuestion(event.getMessage());
				}
				, false);
	}

	@Override
	protected void setConfiguration() {
		this.setConfigWhichMessagesToDelete(WhichMessagesToDelete.ALL);
		this.setConfigWhenToDeleteMessages(WhenToDeleteMessages.NEXT_QUESTION);
	}
}
