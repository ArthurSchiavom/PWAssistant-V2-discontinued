package information.scheduling.messageSchedule;

import information.Bot;
import information.ownerconfiguration.Embeds;
import information.scheduling.manager.RepetitionType;
import information.scheduling.manager.ScheduleManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MessagingScheduler {
    private final String name;
    private final String guildId;
    private final String channelId;
    private final MessageCreateData message;
    private final ScheduleManager scheduleManager;
    private boolean started = false;

    public MessagingScheduler(String name, String guildId, String channelId, MessageCreateData message, RepetitionType repetitionType, List<Integer> scheduleDays, int hour, int minute) {
        this.name = name;
        this.guildId = guildId;
        this.channelId = channelId;
        this.message = message;
        List<Integer> scheduleDaysCopy = new ArrayList<>(scheduleDays);
        this.scheduleManager = new ScheduleManager(repetitionType, scheduleDaysCopy, hour, minute);
    }

    public String getName() {
        return name;
    }

    public String getGuildId() {
        return guildId;
    }

    public String getChannelId() {
        return channelId;
    }

    public MessageCreateData getMessage() {
        return message;
    }

    public ScheduleManager getScheduleManager() {
        return scheduleManager;
    }

    /**
     * If it's past the "next" update time, sends the message.
     */
    public void checkSchedule(Calendar currentTime) {
        if (scheduleManager.isPastExecutionTime(currentTime)) {
            try {
                Bot.getJdaInstance().getGuildById(guildId).getTextChannelById(channelId).sendMessage(message).queue(v -> {
                }, e -> {
                });
            } catch (Exception e) {
            }
            scheduleManager.updateNextExecutionTime();
        }
    }

    public boolean isThisScheduler(String guildId, String scheduleName) {
        return this.guildId.equals(guildId) && this.name.equalsIgnoreCase(scheduleName);
    }

    public String buildShortVisualRepresentation() {
        StringBuilder sb = new StringBuilder();
        String time = Utils.calendarToTimeDisplay(scheduleManager.getNextExecutionTime());
        sb.append("**").append(name).append("**: ")
                .append(scheduleManager.getRepetitionType().toString())
                .append(" - __").append(scheduleManager.getTargetDaysFullDisplay())
                .append("__ at __").append(time).append("__");
        return sb.toString();
    }

    public MessageCreateData buildFullVisualRepresentation() {
        StringBuilder sb = new StringBuilder();
        String time = Utils.calendarToTimeDisplay(scheduleManager.getNextExecutionTime());
        String messageDisplay = message.getContent();
        if (messageDisplay.length() > 900)
            messageDisplay = messageDisplay.substring(0, 900);

        EmbedBuilder eb = new EmbedBuilder();
        Embeds.configDefaultEmbedColor(eb);
        eb.setTitle("Schedule **" + name + "**");

        sb.append("**Repetition**: ").append(scheduleManager.getRepetitionType().toString())
                .append(" - ").append(scheduleManager.getTargetDaysFullDisplay())
                .append("\n**Time**: ").append(time)
                .append("\n**Channel**: ").append(Bot.getJdaInstance()
                .getGuildById(guildId).getTextChannelById(channelId).getAsMention())
                .append("\n**Message**: ").append(messageDisplay);

        eb.setDescription(sb.toString());
        return MessageCreateData.fromEmbeds(eb.build());
    }
}
