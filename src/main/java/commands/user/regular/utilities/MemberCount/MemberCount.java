package commands.user.regular.utilities.MemberCount;

import commands.base.Category;
import commands.base.CommandWithoutSubCommands;
import commands.user.regular.utilities.serverInfo.ServerInfo;
import information.ownerconfiguration.Embeds;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MemberCount extends CommandWithoutSubCommands {

	public MemberCount() {
		super(Category.UTILITY
				, "Information about the amount members of this server."
				, null
				, true);
		this.addName("MemberCount");
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		String membersInfo = ServerInfo.calcMembersInfo(event.getGuild());
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Server members").setDescription(membersInfo);
		Embeds.configDefaultEmbedColor(eb);
		event.getChannel().sendMessageEmbeds(eb.build()).queue();
	}
}
