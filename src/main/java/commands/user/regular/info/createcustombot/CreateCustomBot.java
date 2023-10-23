package commands.user.regular.info.createcustombot;

import commands.base.Category;
import commands.base.CommandWithoutSubCommands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CreateCustomBot extends CommandWithoutSubCommands {

    public CreateCustomBot() {
        super(Category.INFORMATION
                , "What do you think of having your own custom Discord bot?"
                , null
                , false);
        this.addName("CreateCustomBot");
        this.addName("CustomBot");
        this.buildHelpMessage();
    }

    @Override
    protected void runCommandActions(MessageReceivedEvent event) {
        event.getChannel().sendMessage("What do you think of having your own custom Discord bot? https://www.youtube.com/watch?v=rU1PzAmn5AY" +
                "\n\nI'm selling Discord bots at affordable prices on <https://www.fiverr.com/share/bk430m>!" +
                "\nAlso, if you want 20% off your first purchase on Fiverr, register yourself here: <http://www.fiverr.com/s2/1829c72e5f>" +
                "\n**Have fun!**").queue();
    }
}
