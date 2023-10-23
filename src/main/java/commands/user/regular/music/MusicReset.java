package commands.user.regular.music;

import commands.base.Command;
import commands.base.CommandWithoutSubCommands;
import commands.base.Requirement;
import music.PlayerManager;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MusicReset extends CommandWithoutSubCommands {
	public MusicReset(Command superCommand) {
		super(superCommand.getCategory()
				, "Clear the music queue and stop playing."
				, null
				, false
				, superCommand);
		this.addName("Reset");
		this.getRequirementsManager().setRequirements(Requirement.ADMIN, Requirement.GUILD_HAS_MUSIC_MANAGER_ACTIVE);
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		PlayerManager.getInstance().unregisterMusicManager(event.getGuild().getIdLong());
		event.getMessage().addReaction(Emoji.fromUnicode("\uD83D\uDC4C")).queue();
	}
}
