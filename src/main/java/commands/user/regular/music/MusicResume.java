package commands.user.regular.music;

import commands.base.Command;
import commands.base.CommandWithoutSubCommands;
import commands.base.Requirement;
import events.MessageReceivedEvent;
import music.GuildMusicManager;
import music.PlayerManager;
import net.dv8tion.jda.api.entities.Guild;

public class MusicResume extends CommandWithoutSubCommands {
	public MusicResume(Command superCommand) {
		super(superCommand.getCategory()
				, "Pause the music."
				, null
				, false
				, superCommand);
		this.addName("Resume");
		this.getRequirementsManager().setRequirements(Requirement.GUILD_HAS_MUSIC_MANAGER_ACTIVE);
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		Guild guild = event.getGuild();
		GuildMusicManager musicManager = PlayerManager.getInstance().getInstance()
				.getGuildMusicManager(guild.getIdLong());

		musicManager.player.setPaused(false);
		event.getMessage().addReaction("▶").queue();
	}
}
