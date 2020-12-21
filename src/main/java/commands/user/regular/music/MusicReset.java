package commands.user.regular.music;

import commands.base.Command;
import commands.base.CommandWithoutSubCommands;
import commands.base.Requirement;
import events.MessageReceivedEvent;
import music.PlayerManager;

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
		event.getMessage().addReaction("\uD83D\uDC4C").queue();
	}
}
