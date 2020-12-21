package commands.user.regular.music;

import commands.base.Command;
import commands.base.CommandWithoutSubCommands;
import events.MessageReceivedEvent;

public class MusicPlayNext extends CommandWithoutSubCommands {
	public MusicPlayNext(Command superCommand) {
		super(superCommand.getCategory()
				, "Have the bot play some music. The bot must be in a voice channel already!"
				, "Youtube music link"
				, false
				, superCommand);
		this.addName("PlayNext");
		this.buildHelpMessage();
	}

	@SuppressWarnings("Duplicates")
	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		String args = this.extractArgumentsOnly(event.getMessage().getContentDisplay());
		MusicPlay.play(args, event, true);
	}
}
