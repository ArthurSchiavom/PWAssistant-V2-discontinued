package commands.user.admin.moderation.warning;

import commands.base.Category;
import commands.base.CommandWithSubCommands;

public class Warning extends CommandWithSubCommands {
	public Warning() {
		super(Category.UTILITY, "Manage user users");
		this.addName("Warning");
		this.addName("W");

	}
}
