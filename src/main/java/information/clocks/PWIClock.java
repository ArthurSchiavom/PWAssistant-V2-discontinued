package information.clocks;

import database.tables.PWIClockTable;
import information.Bot;
import information.PWIServer;
import information.ownerconfiguration.Embeds;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.MessageBuilder;
import utils.Utils;

import java.util.*;

public class PWIClock extends Clock {
	private static Map<PWIServer, String> serverTimesDisplay = new HashMap<>();
	private List<PWIServer> pwiServers = new ArrayList<>();
	private final String guildId;
	private final String channelId;
	private final String msgId;

	public PWIClock(List<PWIServer> pwiServers, String guildId, String channelId, String msgId) {
		this.pwiServers.addAll(pwiServers);
		Collections.sort(this.pwiServers);
		this.guildId = guildId;
		this.channelId = channelId;
		this.msgId = msgId;
		if (serverTimesDisplay.isEmpty()) {
			serverTimesDisplay.put(PWIServer.DAWNGLORY, "not yet calculated");
			serverTimesDisplay.put(PWIServer.ETHERBLADE, "not yet calculated");
			serverTimesDisplay.put(PWIServer.TWILIGHT_TEMPLE, "not yet calculated");
			serverTimesDisplay.put(PWIServer.TIDESWELL, "not yet calculated");
		}
		updatePWITimes();
		updateClockMessage(Bot.getJdaInstance());
	}

	public PWIClock(List<PWIServer> pwiServers, long guildId, long channelId, long msgId) {
		this(pwiServers, Long.toString(guildId), Long.toString(channelId), Long.toString(msgId));
	}

	@Override
	public boolean updateClockMessage(JDA jda) {
		boolean shouldKeepClock = true;
		StringBuilder sb = new StringBuilder();
		for (PWIServer server : pwiServers) {
			sb.append("\n\n").append(serverTimesDisplay.get(server));
		}

		EmbedBuilder eb = new EmbedBuilder()
				.setTitle(":clock1: PWI Clock")
				.setDescription(sb.toString());
		Embeds.configDefaultEmbedColor(eb);

		return this.editClockMessageSync(jda, new MessageBuilder(eb).build());
	}

	public synchronized static void updatePWITimes() {
		for (PWIServer server : serverTimesDisplay.keySet()) {
			serverTimesDisplay.replace(server, "**" + server.getName() + "**: "
					+ Utils.calendarToTimeDisplay(server.getCurrentTime()));
		}
	}

	public List<PWIServer> getPwiServers() {
		return new ArrayList<>(pwiServers);
	}

	@Override
	public String getGuildId() {
		return guildId;
	}

	@Override
	public String getChannelId() {
		return channelId;
	}

	@Override
	public String getMessageId() {
		return msgId;
	}

	@Override
	public boolean removeFromDatabase() {
		return PWIClockTable.getInstance().remove(msgId);
	}

	@Override
	public boolean addToDatabase() {
		return PWIClockTable.getInstance().add(this);
	}
}
