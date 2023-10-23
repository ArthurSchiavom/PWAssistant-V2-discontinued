package commands.user.regular.info.help;

import commands.CommandExecutor;
import commands.base.Category;
import commands.base.Command;
import commands.base.CommandWithoutSubCommands;
import information.ownerconfiguration.Commands;
import information.ownerconfiguration.Guilds;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Help command.
 */
public class Help extends CommandWithoutSubCommands {

	private CommandExecutor commandExecutor;
	private final String ADMIN_HELP_ARG = "admin";
	private final String OWNER_HELP_ARG = "owner";

	public Help() {
		super(Category.INFORMATION
				, "See the commands list. Use `" + Commands.getCommandPrefix() + "help admin` to see admin commands."
				, null
				, false);
		this.addName("Help");
		this.buildHelpMessage();

		commandExecutor = CommandExecutor.getInstance();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		String arguments = this.extractArgumentsOnly(event.getMessage().getContentDisplay());

		if (arguments == null) {
			if (Guilds.isMainGuild(event))
				event.getChannel().sendMessage(commandExecutor.getRegularUserHelpMainGuild()).queue();
			else
				event.getChannel().sendMessage(commandExecutor.getRegularUserHelp()).queue();
		}
		else {
			arguments = arguments.toLowerCase();

			if (arguments.equalsIgnoreCase(ADMIN_HELP_ARG)) {
				if (Guilds.isMainGuild(event))
					event.getChannel().sendMessage(commandExecutor.getAdminUserHelpMainGuild()).queue();
				else
					event.getChannel().sendMessage(commandExecutor.getAdminUserHelp()).queue();
			}
			else if (arguments.equalsIgnoreCase(OWNER_HELP_ARG)) {
				event.getChannel().sendMessage(commandExecutor.getOwnerHelp()).queue();
			}
			else {
				boolean isCommandHelp = false;
				for (Command command : commandExecutor.getCommandsRegistered()) {
					Command commandFound = command.isThisCommand(arguments);
					if (commandFound != null) {
						event.getChannel().sendMessage(commandFound.getHelpMessage()).queue();
						isCommandHelp = true;
						break;
					}
				}
				if (!isCommandHelp)
					event.getChannel().sendMessage(commandExecutor.getRegularUserHelp()).queue();
			}
		}
	}
}
