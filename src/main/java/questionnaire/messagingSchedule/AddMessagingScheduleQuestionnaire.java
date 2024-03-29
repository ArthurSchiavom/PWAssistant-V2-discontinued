package questionnaire.messagingSchedule;

import information.CacheModificationSuccessState;
import information.scheduling.manager.RepetitionType;
import information.scheduling.messageSchedule.MessagingScheduler;
import information.scheduling.messageSchedule.MessagingSchedulerRegister;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import questionnaire.base.Questionnaire;
import questionnaire.base.WhenToDeleteMessages;
import questionnaire.base.WhichMessagesToDelete;
import utils.Utils;

import java.util.List;

public class AddMessagingScheduleQuestionnaire extends Questionnaire {

	private String name;
	private RepetitionType repetitionType;
	private List<Integer> days;
	private int[] hourMinute;
	private String channelId;
	private String guildId;
	public AddMessagingScheduleQuestionnaire() {
		this.addQuestion("What's the schedule name?"
				, event -> {
					name = event.getMessage().getContentRaw();
					guildId = event.getGuild().getId();
					if (name.length() > 20) {
						event.getChannel().sendMessage("The name can't have more than 20 characters. What's the schedule name?").queue();
					}
					else {
						MessagingScheduler messagingScheduler = MessagingSchedulerRegister.getSchedule(guildId, name);
						if (messagingScheduler == null)
							this.nextQuestion();
						else
							event.getChannel().sendMessage("There's already a schedule with that name! Please choose a different one." +
									"\nWhat's the schedule name?").queue(msg -> this.queueMessageToDeleteNextQuestion(msg));
					}
				}
				, false);

		this.addQuestion("Is the repetition weekly (select days of week) or monthly (select days of month)?" +
						"\nReply with `weekly` or `monthly`."
				, event -> {
					String reply = event.getMessage().getContentRaw();
					if (reply.equalsIgnoreCase("weekly")) {
						repetitionType = RepetitionType.WEEKLY;
						this.nextQuestion();
					}
					else if (reply.equalsIgnoreCase("monthly")) {
						repetitionType = RepetitionType.MONTHLY;
						this.goForwardNQuestions(2);
					}
					else {
						event.getChannel().sendMessage("I did not understand that. You must reply with either `weekly` or `monthly`.").queue(msg -> this.queueMessageToDeleteNextQuestion(msg));
					}
				}
				, false);

		this.addQuestion("Which are the days of the week? (`monday`, `tuesday`, `wednesday`, `thursday`, `friday`, `saturday`, `sunday`)"
				, event -> {
					days = Utils.getMentionedDaysOfWeek(event.getMessage().getContentRaw());
					if (days.isEmpty())
						event.getChannel().sendMessage("Failed to understand that. Please choose 1+ of the following: `monday`, `tuesday`, `wednesday`, `thursday`, `friday`, `saturday`, `sunday`").queue(msg -> this.queueMessageToDeleteNextQuestion(msg));
					else
						this.goForwardNQuestions(2);
				}
				, false);

		this.addQuestion("Which are the days of the month? **__Reply with only numbers separated by white spaces!!__** (`1 5 18 30`)" +
						"\nNote that if you choose a day that a month doesn't have," +
						" the closest smaller day will be chosen." +
						" For example, if you choose day 31 but the month only has 29 days, the message will be sent on day 29."
				, event -> {
					String reply = event.getMessage().getContentRaw();
					days = Utils.extractNumbersSeparatedByWhitespace(reply);
					if (days.isEmpty())
						event.getChannel().sendMessage("I could not understand that. Input the days in numbers and separated by **__whitespaces__**").queue(msg -> this.queueMessageToDeleteNextQuestion(msg));
					else
						this.nextQuestion();
				}
				, false);

		this.addQuestion("What's the time in 24H format? Example: `22:15`"
				, event -> {
					String reply = event.getMessage().getContentRaw();
					hourMinute = Utils.TimeStringToIntArray(reply);
					if (hourMinute != null)
						this.nextQuestion();
					else
						event.getChannel().sendMessage("Invalid time. What's the time in 24H format? Example: `22:15`").queue(msg -> this.queueMessageToDeleteNextQuestion(msg));
				}
				, false);

		this.addQuestion("Which channel should the messages be sent on? (example: #announcements)"
				, event -> {
					List<GuildMessageChannel> textChannels = event.getMessage().getMentions().getChannels(GuildMessageChannel.class);
					if (textChannels.isEmpty()) {
						TextChannel channel = event.getChannel().asTextChannel();
						channel.sendMessage("I couldn't understand that. Please mention the channel, for example: " + channel.getAsMention()).queue(msg -> this.queueMessageToDeleteNextQuestion(msg));
					}
					else {
						channelId = textChannels.get(0).getId();
						this.nextQuestion();
					}
				}
				, false);

		this.addQuestion("And finally! What's the message to send?"
				, event -> {
					String messageString = event.getMessage().getContentRaw();
					MessageCreateData message = MessageCreateData.fromContent(messageString);
					MessagingScheduler messagingScheduler = new MessagingScheduler(name
							, event.getGuild().getId()
							, channelId, message, repetitionType, days, hourMinute[0], hourMinute[1]);
					CacheModificationSuccessState successState = MessagingSchedulerRegister.register(messagingScheduler, true);
					String reply = "Unknown error";
					switch (successState) {
						case SUCCESS:
							reply = "Message scheduled! <:png13:489925231356411904>";
							break;
						case FAILED_DATABASE_MODIFICATION:
							reply = "Error! Failed to access my database! D:";
							break;
						case FAILED_CACHE_MODIFICATION:
							reply = "There's already a schedule with that name!";
					}
					event.getChannel().sendMessage(reply).queue();
					this.nextQuestion();
				}
				, false);
	}

	@Override
	protected void setConfiguration() {
		this.setConfigWhichMessagesToDelete(WhichMessagesToDelete.ALL);
		this.setConfigWhenToDeleteMessages(WhenToDeleteMessages.NEXT_QUESTION);
	}
}
