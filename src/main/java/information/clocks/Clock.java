package information.clocks;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import utils.MessageUnavailableException;
import utils.Utils;

public abstract class Clock {
	/**
	 * Updates this clock's message.
	 *
	 * @param jda The JDA instance to use.
	 * @return If the clock should be kept active.
	 */
	public abstract boolean updateClockMessage(JDA jda);
	public abstract String getGuildId();
	public abstract String getChannelId();
	public abstract String getMessageId();
	public abstract boolean removeFromDatabase();
	public abstract boolean addToDatabase();

	/**
	 * Updates this clock's message.
	 *
	 * @param jda The JDA instance to use.
	 * @param newMsg The new clock message.
	 * @return If the clock should be kept active.
	 */
	protected boolean editClockMessageSync(JDA jda, Message newMsg) {
		boolean shouldKeepClock = true;
		try {
			Utils.retrieveMessageIfAvailable(jda, getGuildId(), getChannelId(), getMessageId())
					.editMessage(newMsg).complete();
		} catch (MessageUnavailableException e) {
			shouldKeepClock = false;
		} catch (Exception e) {/* Not connected to Discord */}

		return shouldKeepClock;
	}
}
