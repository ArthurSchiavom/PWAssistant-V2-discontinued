package commands.user.regular.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import commands.base.Command;
import commands.base.CommandWithoutSubCommands;
import commands.base.Requirement;
import events.MessageReceivedEvent;
import music.GuildMusicManager;
import music.PlayerManager;
import music.TrackScheduler;

public class MusicSkip extends CommandWithoutSubCommands {
	public MusicSkip(Command superCommand) {
		super(superCommand.getCategory()
				, "Ask the bot to skip the current song."
				, null
				, false
				, superCommand);
		this.addName("Skip");
		this.addName("Next");
		this.getRequirementsManager().setRequirements(Requirement.GUILD_HAS_MUSIC_MANAGER_ACTIVE);
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild().getIdLong());

		TrackScheduler scheduler = musicManager.scheduler;
		AudioPlayer player = musicManager.player;

		if (player.getPlayingTrack() == null) {
			event.getChannel().sendMessage("Not playing anything!").queue();
			return;
		}
		scheduler.nextTrack(true);

		event.getMessage().addReaction("⏩").queue();
	}
}
