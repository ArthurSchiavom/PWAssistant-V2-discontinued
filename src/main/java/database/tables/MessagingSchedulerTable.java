package database.tables;

import database.base.Table;
import database.base.TableValue;
import information.scheduling.manager.RepetitionType;
import information.scheduling.manager.ScheduleManager;
import information.scheduling.messageSchedule.MessagingScheduler;
import information.scheduling.messageSchedule.MessagingSchedulerRegister;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessagingSchedulerTable extends Table {
	private static final String DAYS_LIST_SEPARATOR = ",";

	private static final String VALUE_SCHEDULER_NAME = "SchedulerName";
	private static final String VALUE_GUILD_ID = "GuildId";
	private static final String VALUE_CHANNEL_ID = "ChannelId";
	private static final String VALUE_MESSAGE = "Message";
	private static final String VALUE_REPETITION_TYPE = "RepetitionType";
	private static final String VALUE_SCHEDULE_DAYS = "ScheduleDays";
	private static final String VALUE_HOUR = "Hour";
	private static final String VALUE_MINUTE = "Minute";

	private static MessagingSchedulerTable instance;
	private MessagingSchedulerTable() {}
	/**
	 * Initializes this table.
	 *
	 * @return The table instance.
	 */
	public static MessagingSchedulerTable initialize() {
		if (instance == null) {
			instance = new MessagingSchedulerTable();
			instance.setTableName("MessagingSchedulers");
			instance.addTableValue(new TableValue(VALUE_SCHEDULER_NAME, "varchar(2000)"));
			instance.addTableValue(new TableValue(VALUE_GUILD_ID, "bigint"));
			instance.addTableValue(new TableValue(VALUE_CHANNEL_ID, "bigint"));
			instance.addTableValue(new TableValue(VALUE_MESSAGE, "varchar(2000)"));
			instance.addTableValue(new TableValue(VALUE_REPETITION_TYPE, "varchar(20)"));
			instance.addTableValue(new TableValue(VALUE_SCHEDULE_DAYS, "varchar(100)"));
			instance.addTableValue(new TableValue(VALUE_HOUR, "TINYINT"));
			instance.addTableValue(new TableValue(VALUE_MINUTE, "TINYINT"));
		}
		return instance;
	}
	public static MessagingSchedulerTable getInstance() {
		return instance;
	}

	@Override
	public boolean loadIntoMemory() {
		ResultSet rs = this.executeSelectAll();
		if (rs != null) {
			try {
				while (rs.next()) {
					String schedulerName = rs.getString(1);
					String guildId = Long.toString(rs.getLong(2));
					String channelId = Long.toString(rs.getLong(3));
					String messageString = rs.getString(4);
					Message message = new MessageBuilder(messageString).build();
					RepetitionType repetitionType = RepetitionType.fromString(rs.getString(5));
					List<Integer> scheduleDays = daysFromDatabaseList(rs.getString(6));
					int hour = rs.getInt(7);
					int minute = rs.getInt(8);

					MessagingScheduler newMessagingScheduler = new MessagingScheduler(schedulerName, guildId
							, channelId, message, repetitionType, scheduleDays, hour, minute);
					MessagingSchedulerRegister.register(newMessagingScheduler, false);
				}
			} catch (SQLException e) {
				System.out.println("FAILED TO ACCESS DATABASE");
				return false;
			}
		}
		return true;
	}

	private String calculateDaysListDatabase(List<Integer> days) {
		StringBuilder sb = new StringBuilder();
		sb.append(days.get(0));
		for (int i = 1; i < days.size(); i++) {
			sb.append(DAYS_LIST_SEPARATOR).append(days.get(i));
		}
		return sb.toString();
	}

	private List<Integer> daysFromDatabaseList(String daysList) {
		String[] daysString = daysList.split(DAYS_LIST_SEPARATOR);
		List<Integer> daysInt = new ArrayList<>();
		for (String dayString : daysString) {
			daysInt.add(Integer.parseInt(dayString));
		}
		return daysInt;
	}

	public boolean add(MessagingScheduler messagingScheduler) {
		ScheduleManager scheduleManager = messagingScheduler.getScheduleManager();
		String daysListString = calculateDaysListDatabase(messagingScheduler.getScheduleManager().getTargetDays());
		return this.add__Internal(
				messagingScheduler.getName()
				, Long.parseLong(messagingScheduler.getGuildId())
				, Long.parseLong(messagingScheduler.getChannelId())
				, messagingScheduler.getMessage().getContentRaw()
				, scheduleManager.getRepetitionType().toString()
				, daysListString
				, scheduleManager.getHour()
				, scheduleManager.getMinute());
	}

	public boolean remove(String guildId, String schedulerName) {
		String[] valuesNames = {VALUE_GUILD_ID, VALUE_SCHEDULER_NAME};
		Integer result = this.remove__Internal(valuesNames, Long.parseLong(guildId), schedulerName);
		return result != null;
	}
}
