package commands.user.regular.music;

import commands.base.Command;
import commands.base.CommandWithoutSubCommands;
import commands.base.Requirement;
import music.PlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MusicLeave extends CommandWithoutSubCommands {
	public MusicLeave(Command superCommand) {
		super(superCommand.getCategory()
				, "Make the bot leave the voice channel."
				, null
				, false
				, superCommand);
		this.addName("Leave");
		this.addName("L");
		this.getRequirementsManager().setRequirements(Requirement.BOT_CONNECTED_TO_VOICE_CHANNEL);
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		Guild guild = event.getGuild();

		PlayerManager.getInstance().unregisterMusicManager(guild.getIdLong());
		guild.getAudioManager().closeAudioConnection();
		event.getMessage().addReaction(Emoji.fromUnicode("\uD83D\uDC4B")).queue();
	}
}
