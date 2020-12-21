package error;

import information.Bot;
import information.ownerconfiguration.Guilds;
import information.ownerconfiguration.Users;

public class Reporter {
	public static void report(String msg) {
		Bot.getJdaInstance().getUserById(Users.ownerId).openPrivateChannel().queue(channel -> {
					channel.sendMessage(msg).queue();
				}
				, exception -> {
					System.out.println("Failed to report error: " + msg);
				});
	}

	public static void reportToPWIKingdom(String msg) {
		Guilds.getMainGuild().getTextChannelById("289853288940044290").sendMessage(msg).queue();
	}
}
