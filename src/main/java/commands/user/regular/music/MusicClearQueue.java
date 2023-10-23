package commands.user.regular.music;

import commands.base.Command;
import commands.base.CommandWithoutSubCommands;
import commands.base.Requirement;
import music.GuildMusicManager;
import music.PlayerManager;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MusicClearQueue extends CommandWithoutSubCommands {
	public MusicClearQueue(Command superCommand) {
		super(superCommand.getCategory()
				, "Clear the music queue but keep playing the current song."
				, null
				, false
				, superCommand);
		this.addName("ClearQueue");
		this.getRequirementsManager().setRequirements(Requirement.ADMIN, Requirement.SAME_VOICE_CHANNEL);
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		GuildMusicManager musicManager = PlayerManager.getInstance().getGuildMusicManager(event.getGuild().getIdLong());

		if (musicManager == null) {
			event.getChannel().sendMessage("**There's nothing playing.**").queue();
			return;
		}

		musicManager.scheduler.clearQueue();
		event.getMessage().addReaction(Emoji.fromUnicode("\uD83D\uDC4C")).queue();
	}
}
