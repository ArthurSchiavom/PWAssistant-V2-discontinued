package commands.user.admin.pwi.addClock;

import commands.base.Category;
import commands.base.CommandWithoutSubCommands;
import commands.base.Requirement;
import error.Reporter;
import events.MessageReceivedEvent;
import information.PWIServer;
import information.clocks.Clock;
import information.clocks.ClockRegister;
import information.clocks.PWIClock;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.List;

public class CreatePWIClock extends CommandWithoutSubCommands {
	public CreatePWIClock() {
		super(Category.PWI
				, "Adds a clock with live PWI servers' times."
				, "Server1, Server2,..."
				, true);
		this.addName("CreatePWIClock");
		this.addName("PWIClock");
		this.addExample("Etherblade, Dawnglory", "Adds a clock with live times for Etherblade and Dawnglory");
		this.getRequirementsManager().addRequirement(Requirement.ADMIN);
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		MessageChannel channel = event.getChannel();
		Message msg = event.getMessage();
		String args = this.extractArgumentsOnly(msg.getContentRaw());
		try {msg.delete().complete();} catch (Exception e) {}
		if (args == null) {
			channel.sendMessage("You must specify the PWI servers!").complete();
			return;
		}

		List<PWIServer> servers = PWIServer.getServers(args);
		if (servers.isEmpty()) {
			channel.sendMessage("You must specify the PWI servers!").complete();
			return;
		}

		String guildId = event.getGuild().getId();
		String channelId = channel.getId();
		MessageEmbed loadingClockEmbed = new EmbedBuilder()
				.setTitle("【ＣＯＯＬ　ＣＬＯＣＫ　ＬＯＡＤＩＮＧ】")
				.setDescription("This can take a few seconds.")
				.build();
		Message loadingMsg = channel.sendMessage(loadingClockEmbed).complete();
		String msgId = loadingMsg.getId();
		Clock newClock = new PWIClock(servers, guildId, channelId, msgId);

		try {
			boolean success = ClockRegister.getInstance().register(newClock, true);
			if (!success) {
				channel.sendMessage("The max number of clocks in this server has been reached: " + ClockRegister.MAX_CLOCKS_PER_GUILD).complete();
				loadingMsg.delete().complete();
			}
		} catch (Exception e) {
			e.printStackTrace();
			channel.sendMessage("Failed to add clock. Please try again.").queue();
			Reporter.report("Failed to add clock. Check logs");
		}
	}
}
