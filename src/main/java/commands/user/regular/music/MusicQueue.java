package commands.user.regular.music;

import commands.base.Command;
import commands.base.CommandWithoutSubCommands;
import commands.base.Requirement;
import information.ownerconfiguration.Embeds;
import music.GuildMusicManager;
import music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import utils.Utils;

import java.util.List;

public class MusicQueue extends CommandWithoutSubCommands {
	public MusicQueue(Command superCommand) {
		super(superCommand.getCategory()
				, "See the queued songs."
				, null
				, false
				, superCommand);
		this.addName("Queue");
		this.getRequirementsManager().setRequirements(Requirement.GUILD_HAS_MUSIC_MANAGER_ACTIVE);
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		GuildMusicManager musicManager = PlayerManager.getInstance().getGuildMusicManager(event.getGuild().getIdLong());

		List<String> tracksDisplayable = musicManager.scheduler.retrieveQueueTracksDisplayableWithLinkMarkdown();

		//TODO - Utils.extractPage();

		String tracksDisplay;
		if (!tracksDisplayable.isEmpty())
			tracksDisplay = Utils.getListDisplayNumbered(". ", tracksDisplayable.toArray()).toString();
		else
			tracksDisplay = "The queue is empty.";

		EmbedBuilder eb = new EmbedBuilder().setTitle("Queue").setDescription(tracksDisplay);
		Embeds.configDefaultEmbedColor(eb);
		event.getChannel().sendMessageEmbeds(eb.build()).queue();
	}
}
