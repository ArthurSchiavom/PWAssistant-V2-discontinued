package commands;

import commands.base.Category;
import commands.base.Command;
import commands.base.Requirement;
import commands.user.admin.moderation.clear.Clear;
import commands.user.admin.pwi.addClock.CreatePWIClock;
import commands.user.admin.utilities.countdown.Countdown;
import commands.user.admin.utilities.schedule.Schedule;
import commands.user.admin.utilities.trigger.Trigger;
import commands.user.owner.fun.stream.Stream;
import commands.user.owner.utils.broadcastToOwners.BroadcastToOwners;
import commands.user.owner.utils.manageServers.ManageServers;
import commands.user.regular.configuration.toggle.free.games.notification.ToggleFreeGamesNotification;
import commands.user.regular.configuration.toggleNSFW.ToggleNSFW;
import commands.user.regular.fun.lmgtfy.LMGTFY;
import commands.user.regular.fun.randomNumber.RandomNumber;
import commands.user.regular.fun.say.Say;
import commands.user.regular.info.about.About;
import commands.user.regular.info.createcustombot.CreateCustomBot;
import commands.user.regular.info.help.Help;
import commands.user.regular.info.invite.Invite;
import commands.user.regular.info.ping.Ping;
import commands.user.regular.info.support.Support;
import commands.user.regular.pwi.Codes;
import commands.user.regular.pwi.PWIClass;
import commands.user.regular.pwi.PWIItemInfo;
import commands.user.regular.pwi.PWIItemPrice;
import commands.user.regular.pwi.PWIServer;
import commands.user.regular.pwi.PWIServerStatus;
import commands.user.regular.pwi.PWIServerTime;
import commands.user.regular.utilities.MemberCount.MemberCount;
import commands.user.regular.utilities.google.Google;
import commands.user.regular.utilities.react.React;
import commands.user.regular.utilities.serverInfo.ServerInfo;
import information.ownerconfiguration.Commands;
import information.ownerconfiguration.Embeds;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The command executor registers and executes commands. <b>Register the commands on the initialization method</b>.
 */
public class CommandExecutor {
	private static CommandExecutor instance;

	private static final String MAIN_MENU_HELP_THUMBNAIL = "https://live.staticflickr.com/65535/48673315806_a35665d72f_o_d.png";

	private MessageCreateData ownerHelp;
	private MessageCreateData adminUserHelp;
	private MessageCreateData regularUserHelp;
	private MessageCreateData adminUserHelpMainGuild;
	private MessageCreateData regularUserHelpMainGuild;

	private List<Command> commands = new ArrayList<>();
	private List<String> commandsNames = new ArrayList<>();

	private CommandExecutor() {}

	/**
	 * Initializes an instance of this singleton instance if it wasn't initialized yet.
	 */
	public static void initialize() {
		if (instance == null) {
			instance = new CommandExecutor();
		}

		instance.registerCommand(new About());
		instance.registerCommand(new Invite());
		instance.registerCommand(new CreateCustomBot());
		instance.registerCommand(new React());
		instance.registerCommand(new Help());
		instance.registerCommand(new Support());
//		instance.registerCommand(new Donate());
		instance.registerCommand(new Ping());

		instance.registerCommand(new Codes());
		instance.registerCommand(new PWIServerTime());
		instance.registerCommand(new PWIServerStatus());
		instance.registerCommand(new PWIItemInfo());
		instance.registerCommand(new PWIItemPrice());
		instance.registerCommand(new PWIClass());
		instance.registerCommand(new PWIServer());

		instance.registerCommand(new ToggleFreeGamesNotification());
		instance.registerCommand(new ToggleNSFW());

		instance.registerCommand(new ServerInfo());
		instance.registerCommand(new MemberCount());
		instance.registerCommand(new Google());

		instance.registerCommand(new LMGTFY());
		instance.registerCommand(new RandomNumber());
		instance.registerCommand(new Say());


		instance.registerCommand(new Clear());
		instance.registerCommand(new Schedule());
		instance.registerCommand(new Trigger());
		instance.registerCommand(new Countdown());

		instance.registerCommand(new CreatePWIClock());


		instance.registerCommand(new ManageServers());
		instance.registerCommand(new BroadcastToOwners());

		instance.registerCommand(new Stream());

//		instance.registerCommand(new Music());
		instance.updateAllHelp();
	}

	/**
	 * @return The regular user help message.
	 */
	public MessageCreateData getRegularUserHelp() {
		return regularUserHelp;
	}

	/**
	 * @return The main guild regular user help message.
	 */
	public MessageCreateData getRegularUserHelpMainGuild() {
		return regularUserHelpMainGuild;
	}

	/**
	 * @return The main guild admin help message.
	 */
	public MessageCreateData getAdminUserHelpMainGuild() {
		return adminUserHelpMainGuild;
	}

	/**
	 * @return The admin user help message.
	 */
	public MessageCreateData getAdminUserHelp() {
		return adminUserHelp;
	}

	/**
	 * @return The owner help message.
	 */
	public MessageCreateData getOwnerHelp() {
		return ownerHelp;
	}

	/**
	 * @return The commands registered.
	 */
	public List<Command> getCommandsRegistered() {
		return new ArrayList<>(commands);
	}

	/**
	 * Registers a command.
	 * <br><b>Only commands WITHOUT a super-command should be registered.</b>
	 *
	 * @param cmd The command to register.
	 */
	public void registerCommand(Command cmd) {
		commands.add(cmd);
		commandsNames.addAll(cmd.getNames());
	}

	/**
	 * Verifies if the given name is the name of a command already registered.
	 *
	 * @param name The name to verify.
	 * @return If the given name is the name of a command already registered.
	 */
	public boolean isCommandNameRegistered(String name) {
		name.toLowerCase();
		return commandsNames.contains(name);
	}

	/**
	 * @return This singleton's class instance.
	 */
	public static CommandExecutor getInstance() {
		return instance;
	}

	/**
	 * Executes a command request, if it is a proper command request.
	 *
	 * @param msgEvent the message that might be a command request.
	 */
	public void executePossibleCommandRequest(MessageReceivedEvent msgEvent) {
		Command commandToRun = getCommand(msgEvent);
		if (commandToRun != null)
			commandToRun.run(msgEvent);
	}

	/**
	 * Finds the command that the message is requesting, if it is.
	 *
	 * @param msgEvent The event containing the message to analyse.
	 * @return The command request or
	 * <br>null if no command is requested in the event.
	 */
	public Command getCommand(MessageReceivedEvent msgEvent) {
		String message;
		try {
			message = msgEvent.getMessage().getContentDisplay().toLowerCase();
			if (!message.startsWith(Commands.getCommandPrefix()))
				return null;
		} catch (StringIndexOutOfBoundsException e) {
			/* This means that the message wasn't long enough to contain the prefix */
			return null;
		}

		if (message != null) {
			String command = message.substring(Commands.getPrefixNChars());
			for (Command commandRegistered : commands) {
				Command commandToRun = commandRegistered.isThisCommand(command);
				if (commandToRun != null)
					return commandToRun;
			}
		}
		return null;
	}

	/**
	 * Updates all help messages.
	 */
	public void updateAllHelp() {
		updateRegularHelp();
		updateAdminHelp();
		updateOwnerHelp();
	}

	/**
	 * Updates the regular user help message.
	 */
	public void updateRegularHelp() {
		regularUserHelp = MessageCreateData.fromEmbeds(calculateGenericHelp(null, null).build());

		Requirement[] mayAlsoHaveRequirementsMainGuild = {Requirement.MAIN_GUILD};
		regularUserHelpMainGuild = MessageCreateData.fromEmbeds(calculateGenericHelp(null, mayAlsoHaveRequirementsMainGuild).build());
	}

	/**
	 * Updates the regular user help message.
	 */
	public void updateAdminHelp() {
		Requirement[] requirements = {Requirement.ADMIN};
		adminUserHelp = MessageCreateData.fromEmbeds(calculateGenericHelp(requirements, null).build());

		Requirement[] requirementsMainGuild = {Requirement.ADMIN};
		Requirement[] mayAlsoHaveRequirementsMainGuild = {Requirement.MAIN_GUILD};
		adminUserHelpMainGuild = MessageCreateData.fromEmbeds(calculateGenericHelp(requirementsMainGuild, mayAlsoHaveRequirementsMainGuild).build());
	}

	/**
	 * Updates the owner help message.
	 */
	public void updateOwnerHelp() {
		Requirement[] mustHaveRequirements = {Requirement.OWNER};
		Requirement[] mayAlsoHaveRequirements = {Requirement.MAIN_GUILD};
		ownerHelp = MessageCreateData.fromEmbeds(calculateGenericHelp(mustHaveRequirements, mayAlsoHaveRequirements).build());
	}

	/**
	 * Calculates a generic command list help.
	 *
	 * @param mustHaveRequirements The commands must have these requirements. Can be null
	 * @param mayAlsoHaveRequirements The commands may also have these requirements. Can be null
	 */
	private EmbedBuilder calculateGenericHelp(Requirement[] mustHaveRequirements, Requirement[] mayAlsoHaveRequirements) {
		List<Command> commandsToList = getCommands(mustHaveRequirements, mayAlsoHaveRequirements);
		TreeMap<Category, String> categoriesHelp = calcCategoriesHelp(commandsToList);

		EmbedBuilder helpEb = new EmbedBuilder();
		for (Category cat : categoriesHelp.keySet()) {
			helpEb.addField(cat.getName(), categoriesHelp.get(cat), true);
		}
		Utils.adjustEmbedInlineFields(helpEb, 2);

		Embeds.configDefaultEmbedColor(helpEb);
		helpEb.setThumbnail(MAIN_MENU_HELP_THUMBNAIL);
		Embeds.configHelpEmbedFooter(helpEb);
		return helpEb;
	}

	private TreeMap<Category, String> calcCategoriesHelp(List<Command> commands) {
		Map<Category, List<Command>> commandsByCategory = getCommandsByCategory(commands);

		TreeMap<Category, String> categoriesHelp = new TreeMap<>();
		for (Category cat : commandsByCategory.keySet()) {
			List<Command> categoryCommands = commandsByCategory.get(cat);

			if (!categoryCommands.isEmpty()) {
				String catHelp = getCategoryHelpMessage(categoryCommands);
				categoriesHelp.put(cat, catHelp);
			}
		}
		return categoriesHelp;
	}

	/**
	 * Calculates the help message for a category.
	 *
	 * @param commands The category commands.
	 * @return The help message for a category.
	 */
	private String getCategoryHelpMessage(List<Command> commands) {
		StringBuilder sb = new StringBuilder();
		Iterator<Command> it = commands.iterator();
		if (it.hasNext())
			sb.append(" • ").append(it.next().getName());
		it.forEachRemaining(cmd -> {
			sb.append("\n").append(" • ").append(cmd.getName());
		});
		sb.append("\n" + Utils.getInvisibleCharacter());
		return sb.toString();
	}

	/**
	 * Separates a list of commands by category.
	 *
	 * @param allCommands The list of commands to separate.
	 * @return A map of commands by category.
	 */
	public Map<Category, List<Command>> getCommandsByCategory(List<Command> allCommands) {
		Map<Category, List<Command>> result = new HashMap<>();
		for (Category cat : Category.values()) {
			result.put(cat, new ArrayList<Command>());
		}

		for (Command cmd : allCommands) {
			result.get(cmd.getCategory()).add(cmd);
		}
		return result;
	}

	/**
	 * Finds the registered commands with given conditions.
	 *
	 * @param requirements The commands must have these requirements. Can be null
	 * @param mayAlsoHaveRequirements The commands may also have these requirements. Can be null
	 * @return The registered commands that meet the conditions.
	 */
	public List<Command> getCommands(Requirement[] requirements, Requirement[] mayAlsoHaveRequirements) {
		List<Command> result = new ArrayList<>();

		if (requirements == null)
			requirements = new Requirement[0];

		for (Command command : commands) {
			if (command.getRequirementsManager().requiresOnly(requirements, mayAlsoHaveRequirements))
				result.add(command);
		}

		return result;
	}

	/**
	 * Unregisters a command.
	 * <br><b>Note that this doesn't unregister the command from the user help menu</b>
	 *
	 * @param command The command to unregister.
	 */
	public void removeCommand(Command command) {
		commands.remove(command);
	}
}
