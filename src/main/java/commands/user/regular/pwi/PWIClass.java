package commands.user.regular.pwi;

import commands.base.Category;
import commands.base.CommandWithoutSubCommands;
import commands.base.Requirement;
import information.ownerconfiguration.roles.Roles;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import utils.Utils;

import java.util.List;

public class PWIClass extends CommandWithoutSubCommands {
	public PWIClass() {
		super(Category.PWI
				, "Add or remove PWI class roles from yourself."
				, "Class1, Class2,..."
				, false);
		this.addName("Class");
		this.addName("PWIClass");
		this.getRequirementsManager().setRequirements(Requirement.MAIN_GUILD);
		this.addExample("psychic", "If you have the role Psychic, I'll remove it from you and if you don't, I'll add it!");
		this.addExample("blademaster wizard assassin", "If you previously had the role Blademaster, I would remove the role Blademaster and add Wizard and Assassin.");
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		List<Long> mentionedRolesIds = Roles.getPWIClassRolesMentionedIn(event.getMessage().getContentDisplay());
		if (mentionedRolesIds.isEmpty()) {
			event.getChannel().sendMessage("You need to specify the classes to add/remove.").queue();
			return;
		}

		event.getChannel().sendMessage(Utils.batchToggleRoles(event.getGuild(), event.getMember(), mentionedRolesIds)).queue();
	}
}