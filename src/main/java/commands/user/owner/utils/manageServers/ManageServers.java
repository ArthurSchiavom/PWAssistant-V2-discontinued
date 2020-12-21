package commands.user.owner.utils.manageServers;

import commands.base.Category;
import commands.base.CommandWithSubCommands;
import commands.base.Requirement;

public class ManageServers extends CommandWithSubCommands {
	public ManageServers() {
		super(Category.UTILITY
				, "View and modify the bot presence in servers."
		);
		this.addName("ManageServers");
		this.addName("MS");
		this.addSubCommand(new Status(this));
		this.addSubCommand(new Quit(this));
		this.getRequirementsManager().addRequirement(Requirement.OWNER);
		this.buildHelpMessage();
	}
}
