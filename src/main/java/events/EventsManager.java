package events;

import events.operators.GuildMessageDeleteEventOperator;
import events.operators.GuildMessageEventOperator;
import events.operators.MemberJoinedEventOperator;
import events.operators.PrivateMessageEventOperator;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

/**
 * Manages received JDA events.
 */
public class EventsManager extends ListenerAdapter {

    private GuildMessageEventOperator guildMessageEventOperator = new GuildMessageEventOperator();
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        guildMessageEventOperator.processMessageReceived(event);
    }

    private MemberJoinedEventOperator memberJoinedEventOperator = new MemberJoinedEventOperator();
    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        memberJoinedEventOperator.processMemberJoinedEvent(event);
    }

    private PrivateMessageEventOperator privateMessageEventOperator = new PrivateMessageEventOperator();
    @Override
    public void onPrivateMessageReceived(@Nonnull PrivateMessageReceivedEvent event) {
        privateMessageEventOperator.processMessageReceived(event);
    }

    private GuildMessageDeleteEventOperator guildMessageDeleteEventOperator = new GuildMessageDeleteEventOperator();
    @Override
    public void onGuildMessageDelete(@Nonnull GuildMessageDeleteEvent event) {
        guildMessageDeleteEventOperator.processEvent(event);
    }

}
