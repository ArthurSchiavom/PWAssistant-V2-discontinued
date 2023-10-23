package events;

import events.operators.GuildMessageDeleteEventOperator;
import events.operators.GuildMessageEventOperator;
import events.operators.MemberJoinedEventOperator;
import events.operators.PrivateMessageEventOperator;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * Manages received JDA events.
 */
public class EventsManager extends ListenerAdapter {

    private GuildMessageEventOperator guildMessageEventOperator = new GuildMessageEventOperator();
    private PrivateMessageEventOperator privateMessageEventOperator = new PrivateMessageEventOperator();
    private GuildMessageDeleteEventOperator guildMessageDeleteEventOperator = new GuildMessageDeleteEventOperator();
    private MemberJoinedEventOperator memberJoinedEventOperator = new MemberJoinedEventOperator();

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getChannelType() == ChannelType.PRIVATE) {
            privateMessageEventOperator.processMessageReceived(event);
        } else if (event.getChannelType() == ChannelType.TEXT) {
            guildMessageEventOperator.processMessageReceived(event);
        }
    }

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        memberJoinedEventOperator.processMemberJoinedEvent(event);
    }

    @Override
    public void onMessageDelete(@NotNull MessageDeleteEvent event) {
        if (event.isFromGuild()) {
            guildMessageDeleteEventOperator.processEvent(event);
        }
    }
}
