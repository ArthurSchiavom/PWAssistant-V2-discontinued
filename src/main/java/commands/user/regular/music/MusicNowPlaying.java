package commands.user.regular.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import commands.base.Command;
import commands.base.CommandWithoutSubCommands;
import commands.base.Requirement;
import events.MessageReceivedEvent;
import information.ownerconfiguration.Embeds;
import music.GuildMusicManager;
import music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import utils.Utils;

public class MusicNowPlaying extends CommandWithoutSubCommands {
	public MusicNowPlaying(Command superCommand) {
		super(superCommand.getCategory()
				, "Stop playing music and leave the voice channel."
				, null
				, false
				, superCommand);
		this.addName("NowPlaying");
		this.addName("NP");
		this.getRequirementsManager().setRequirements(Requirement.GUILD_HAS_MUSIC_MANAGER_ACTIVE);
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		PlayerManager playerManager = PlayerManager.getInstance();
		Guild guild = event.getGuild();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(guild.getIdLong());

		AudioPlayer player = musicManager.player;
		AudioTrack track = player.getPlayingTrack();
		if (track == null) {
			event.getChannel().sendMessage("**There's nothing playing.**").queue();
			return;
		}
		AudioTrackInfo trackInfo = musicManager.player.getPlayingTrack().getInfo();
		EmbedBuilder eb = new EmbedBuilder()
				.setTitle("NOW PLAYING")
				.setDescription("[" + trackInfo.title + "](" + trackInfo.uri + ") (" + Utils.millisecondsToMinuteSecondDisplay(track.getPosition()) + "/" + Utils.millisecondsToMinuteSecondDisplay(track.getDuration()) + ")");
		Embeds.configDefaultEmbedColor(eb);
		eb.setFooter("\uD83C\uDFB6");
		event.getChannel().sendMessage(eb.build()).queue();
	}
}
